package com.api.gateway.route_config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;


@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p
                        .path("/ecommerce/user/**")
                        .filters( f -> f.rewritePath("/ecommerce/user/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(cb-> cb.setName("userCircuitBreaker")
                                        .setFallbackUri("forward:/users/**")))
                        .uri("lb://USER"))
                .route(p -> p
                        .path("/ecommerce/product/**")
                        .filters( f -> f.rewritePath("/ecommerce/product/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()) )
                        .uri("lb://PRODUCT"))
                .route(p -> p
                        .path("/ecommerce/orders/**")
                        .filters( f -> f.rewritePath("/ecommerce/orders/(?<segment>.*)","/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()) )// pre-defined filter
                        .uri("lb://ORDERS")).build();
    }
}
