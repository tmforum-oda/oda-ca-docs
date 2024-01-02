package com.tmf.pim.iam.ext.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class ApplicationErrorHandler {
    
    public Mono<ServerResponse> handleDownStreamError(TMF637ApiException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCause("Error happened while calling downstream component");
        errorResponse.setInternalServiceMessage("");
        errorResponse.setIsDownStreamError(e.getIsDownstreamException());
        errorResponse.setRequest(e.getRequest());
        errorResponse.setUri(e.getUrl());
        errorResponse.setDownstreamServiceName(e.getComponent());
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        if(e.getCause() == null){
            errorResponse.setDownstreamServiceMessage(e.getReason());
        }else{
            errorResponse.setDownstreamServiceMessage(e.getReason()+" "+e.getCause().toString());
        }

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e.getHttpStatus() != null){
            httpStatus = e.getHttpStatus();
            errorResponse.setErrorCode(httpStatus.toString());
        }

        if(e.getCode().equals("404 NOT_FOUND")){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return  ServerResponse.status(httpStatus)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(errorResponse), ErrorResponse.class);
    }

}
