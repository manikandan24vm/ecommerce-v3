package com.api.gateway.route_config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p
                        .path("/ecommerce/user/**")
                        .filters( f -> f.rewritePath("/ecommerce/user/(?<segment>.*)","/${segment}"))
                        .uri("lb://USER"))
                .route(p -> p
                        .path("/ecommerce/product/**")
                        .filters( f -> f.rewritePath("/ecommerce/product/(?<segment>.*)","/${segment}")
                        )
                        .uri("lb://PRODUCT"))
                .route(p -> p
                        .path("/ecommerce/orders/**")
                        .filters( f -> f.rewritePath("/ecommerce/orders/(?<segment>.*)","/${segment}"))
                        .uri("lb://ORDERS")).build();
    }
}
