package com.tmf.pim.iam.ext.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    private String errorCode;
    private String cause;
    private String internalServiceMessage;
    private Boolean isDownStreamError;
    private String downstreamServiceName;
    private String downstreamServiceMessage;
    private String request;
    private String uri;
    private int status;
}
