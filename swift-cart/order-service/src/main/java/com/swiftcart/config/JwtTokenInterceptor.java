package com.swiftcart.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtTokenInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        // Retrieve the JWT token from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof Object) {

            String token = (String)authentication.getCredentials();
            // Add the JWT token to the request headers
            template.header("Authorization", "Bearer " + token);
        }
    }
}
