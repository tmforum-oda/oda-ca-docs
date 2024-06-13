package com.tmf.pim.iam.ext.utility;

import io.vavr.CheckedFunction0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class HttpOutgoingTrafficInterceptor implements ClientHttpRequestInterceptor {
    
    public static final Logger TMF637_Adapter_Logger = LoggerFactory.getLogger(HttpOutgoingTrafficInterceptor.class);
    
    private int count;
    private ResilienceManager resilienceManager = null;
    public HttpOutgoingTrafficInterceptor(ResilienceManager manager){
        this.resilienceManager = manager;
    }

    private boolean isDebugEnabled(){
        return TMF637_Adapter_Logger.isDebugEnabled();
    }

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        count = 0;
        CheckedFunction0<ClientHttpResponse> decoratedSupplier = resilienceManager.getCaller(() -> {
            TMF637_Adapter_Logger.debug( "checked sending new one " + incrementAndGetCount());
            return interceptInner(request, body, execution);
        }, request.getURI().getHost());
        try {
            return decoratedSupplier.apply();
        }
        catch (Throwable throwable) {
            printError( throwable);
            if(throwable instanceof IOException){
                throw (IOException) throwable;
            }else{
                TMF637_Adapter_Logger.debug( throwable.getClass() + " " + throwable.getMessage());
                throw new IOException(throwable.getMessage(), throwable);
            }
        }
    }

    int incrementAndGetCount(){
        return ++count;
    }

    public ClientHttpResponse interceptInner(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        TMF637_Adapter_Logger.debug("requesting ...");
        logRequest(request, body);
        ClientHttpResponse response = null;
        try{
            response = execution.execute(request, body);
        }catch (IOException e){
            printError(e);
            throw e;
        }

        logResponse(response);
        TMF637_Adapter_Logger.debug( "end of requesting ...");
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (isDebugEnabled()) {
            TMF637_Adapter_Logger.debug("===========================request begin================================================");
            if(!request.getURI().toString().contains("CommunicationId"))
            {
                TMF637_Adapter_Logger.debug("URI         : {}", request.getURI());
            }
            TMF637_Adapter_Logger.debug("Method      : {}", request.getMethod());
            TMF637_Adapter_Logger.debug("Headers     : {}", request.getHeaders());
            TMF637_Adapter_Logger.debug("Request body: {}", new String(body, "UTF-8"));
            TMF637_Adapter_Logger.debug("==========================request end================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (isDebugEnabled()) {
            TMF637_Adapter_Logger.debug( "============================response begin==========================================");
            TMF637_Adapter_Logger.debug( "Status code  : {}", response.getStatusCode());
            TMF637_Adapter_Logger.debug( "Status text  : {}", response.getStatusText());
            TMF637_Adapter_Logger.debug( "Headers      : {}", response.getHeaders());
            TMF637_Adapter_Logger.debug( "=======================response end=================================================");
        }
    }

    public void printError(Throwable t){
        if(isDebugEnabled()){
            TMF637_Adapter_Logger.debug(t.getClass() + " - " + t.getMessage());
            Throwable cause = t.getCause();
            if( cause != null){
                TMF637_Adapter_Logger.debug( cause.getClass() + " - " + cause.getMessage());
            }
        }
    }
}
