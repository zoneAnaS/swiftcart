package com.swiftcart.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p->{
                    return p.path("/swiftcart/users/**","/swiftcart/login","/swiftcart/register")
                            .uri("lb://user-service");
                })
                .route(p->{
                    return p.path("/swiftcart/products/**")
                            .uri("lb://product-service");
                })
                .route(p->{
                    return p.path("/swiftcart/carts/**")
                            .uri("lb://cart-service");
                })
                .route(p->{
                    return p.path("/swiftcart/orders/**")
                            .uri("lb://order-service");
                })
                .build();
    }
}
