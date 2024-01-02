package com.tmf.pim.iam.ext.northbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class BaseHandler {

    public BaseHandler() {
    }
    public static final Logger TMF637_Adapter_Logger = LoggerFactory.getLogger(BaseHandler.class);

    public Mono<ServerResponse> callRoot(ServerRequest request) {
        Mono<ServerResponse> notFound = ServerResponse.ok().build();
        TMF637_Adapter_Logger.info("BASE URL has been triggered");
        return notFound;
    }
}
