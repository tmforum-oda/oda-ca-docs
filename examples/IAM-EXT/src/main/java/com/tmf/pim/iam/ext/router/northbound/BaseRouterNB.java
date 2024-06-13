package com.tmf.pim.iam.ext.router.northbound;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.tmf.pim.iam.ext.northbound.BaseHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BaseRouterNB {

    @Bean
    RouterFunction<ServerResponse> routeBase(BaseHandler baseHandler) {

        return route()
                .path("/base",
                        builder -> builder
                                .DELETE(baseHandler::callRoot)
                ).build();
    }
}
