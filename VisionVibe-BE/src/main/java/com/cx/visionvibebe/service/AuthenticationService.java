package com.cx.visionvibebe.service;

import com.cx.visionvibebe.dto.request.AuthenticationRequest;
import com.cx.visionvibebe.dto.request.IntrospectRequest;
import com.cx.visionvibebe.dto.request.LogoutRequest;
import com.cx.visionvibebe.dto.request.RefreshTokenRequest;
import com.cx.visionvibebe.dto.response.AuthenticationResponse;
import com.cx.visionvibebe.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request) throws JOSEException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException;
}
