package com.cx.visionvibebe.configuration;


import com.cx.visionvibebe.dto.response.ApiResponse;
import com.cx.visionvibebe.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig {
    private final JwtDecoder jwtDecoder;
    private final String[] PUBLIC_ENDPOINTS = {
            "/auth/login", "/auth/introspect", "auth/logout", "/auth/refresh", "/payment-method",
            "/notification/set-to-read/**", "/notification/**", "/paypal", "order/**", "/order",
            "/image/**", "/material", "/color", "/blog/all", "/blog", "/blog/**", "/product-sub-categories",
            "/product-sub-categories/**", "/product-categories", "/product-categories/all", "/product/name",
            "/product/**", "/product/name", "/product-detail", "/product-detail/**", "blog-categories",
            "/blog/carousel", "/ws-notifications/websocket", "/order/pending/**", "/promotion"
    };

    public SecurityConfig(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(PUBLIC_ENDPOINTS)
                        .permitAll()
                        .anyRequest().authenticated());


        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder)
                .jwtAuthenticationConverter(jwtAuthenticationConverter())).authenticationEntryPoint((request, response, authException) -> {
            ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
            response.setStatus(errorCode.getHttpStatusCode().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ApiResponse<?> apiResponse = ApiResponse.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            response.flushBuffer();
        }));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(httpSecurityCorsConfigurer -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
            corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
            corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            httpSecurityCorsConfigurer.configurationSource(source);
        });
        return httpSecurity.build();
    }

    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
