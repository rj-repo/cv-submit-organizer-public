package org.rj.api_gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rj.api_gateway.routing.RouterValidator;
import org.rj.cvsubmitorganizer.common.ApiResponseException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class GatewayAuthenticationFilter implements GatewayFilter {


    private final RouterValidator routerValidator;
    private final WebClient webClient;

    public GatewayAuthenticationFilter(RouterValidator routerValidator, WebClient.Builder webClientBuilder) {
        this.routerValidator = routerValidator;
        this.webClient = webClientBuilder
                .baseUrl("http://auth-service")
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (authMissing(request)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            return webClient.post()
                    .uri("/api/v1/auth/validation")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .exchangeToMono(clientResponse -> {
                        if (!clientResponse.statusCode().is2xxSuccessful()) {
                            return onError(exchange, clientResponse.bodyToMono(ApiResponseException.class), clientResponse.statusCode());
                        }

                        HttpHeaders authHeaders = clientResponse.headers().asHttpHeaders();

                            ServerHttpRequest mutatedRequest = exchange.getRequest()
                                    .mutate()
                                    .headers(httpHeaders -> {
                                        String email = authHeaders.getFirst("X-Email");
                                        String userId = authHeaders.getFirst("X-User-Id");
                                        String internalCallHeader = authHeaders.getFirst("X-Internal-Call");
                                        if (email != null) httpHeaders.add("X-Email", email);
                                        if (userId != null) httpHeaders.add("X-User-Id", userId);
                                        httpHeaders.add("X-Internal-Call",internalCallHeader);
                                    })
                                    .build();

                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(mutatedRequest)
                                .build();

                        return chain.filter(mutatedExchange);
                    });

        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange,
                               Mono<ApiResponseException> apiResponseMono,
                               HttpStatusCode httpStatus) {
        return apiResponseMono.flatMap(apiResponse -> {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(httpStatus);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            try {
                byte[] bytes = new ObjectMapper().writeValueAsBytes(apiResponse);
                DataBuffer buffer = response.bufferFactory().wrap(bytes);
                return response.writeWith(Mono.just(buffer));
            } catch (JsonProcessingException e) {
                return response.setComplete();
            }
        });
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}
