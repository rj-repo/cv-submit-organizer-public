package org.rj.api_gateway.routing;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    public static final List<String> openEndpoints = List.of(
            "api/v1/auth/signup",
            "api/v1/auth/login",
            "api/v1/auth/verification",
            "api/v1/auth/verification/resend",
            "api/v1/auth/validation",
            "api/v1/profile/registration",
            "api/v1/applications/api-docs",
            "api/v1/applications/swagger-ui",
            "api/v1/profile/swagger-ui",
            "api/v1/profile/swagger-ui"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
