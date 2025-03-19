package com.cx.visionvibebe.service.serviceImpl;

import com.cx.visionvibebe.dto.request.AuthenticationRequest;
import com.cx.visionvibebe.dto.request.IntrospectRequest;
import com.cx.visionvibebe.dto.request.LogoutRequest;
import com.cx.visionvibebe.dto.request.RefreshTokenRequest;
import com.cx.visionvibebe.dto.response.AuthenticationResponse;
import com.cx.visionvibebe.dto.response.IntrospectResponse;
import com.cx.visionvibebe.entity.InvalidToken;
import com.cx.visionvibebe.entity.User;
import com.cx.visionvibebe.exception.AppException;
import com.cx.visionvibebe.exception.ErrorCode;
import com.cx.visionvibebe.repository.InvalidTokenRepository;
import com.cx.visionvibebe.repository.UserRepository;
import com.cx.visionvibebe.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${jwt.signerKey}")
    private String singerKey;

    @Value("${jwt.valid-duration}")
    private int validDuration;

    @Value("${jwt.refreshable-duration}")
    private int refreshableDuration;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final InvalidTokenRepository invalidTokenRepository;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) throws JOSEException {
        User user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND));
        boolean isAuthenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = generateToken(user);
        return AuthenticationResponse.builder()
                .isAuthenticated(true)
                .token(token)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), true);
        InvalidToken invalidToken = InvalidToken.builder()
                .tokenId(signedJWT.getJWTClaimsSet().getJWTID())
                .expiryTime(signedJWT.getJWTClaimsSet().getExpirationTime())
                .build();
        invalidTokenRepository.save(invalidToken);
    }

    private String generateToken(User user) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issuer("hyperion")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope", buildScope(user))
                .claim("fullname", user.getFullName())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        JWSSigner jwsSigner = new MACSigner(singerKey.getBytes());
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getRoleName());
                role.getPermissions().forEach(permission -> stringJoiner.add(permission.getPermissionName()));

            });
        }
        return stringJoiner.toString();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        String token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(singerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime;
        if (isRefresh) {
            expiryTime = new Date(signedJWT.getJWTClaimsSet().getExpirationTime().toInstant().plus(refreshableDuration, ChronoUnit.SECONDS).toEpochMilli());
        } else {
            expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        }
        boolean verified = signedJWT.verify(verifier);
        if (!verified && expiryTime.after(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (invalidTokenRepository.findById(signedJWT.getJWTClaimsSet().getJWTID()).isPresent()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyToken(request.getToken(), false);
        InvalidToken invalidToken = InvalidToken.builder()
                .tokenId(signedJWT.getJWTClaimsSet().getJWTID())
                .expiryTime(signedJWT.getJWTClaimsSet().getExpirationTime())
                .build();
        invalidTokenRepository.save(invalidToken);

        User user = userRepository.findById(Long.parseLong(signedJWT.getJWTClaimsSet().getSubject())).orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_FOUND));
        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }
}
