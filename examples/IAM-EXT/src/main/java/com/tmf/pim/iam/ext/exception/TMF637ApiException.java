package com.tmf.pim.iam.ext.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TMF637ApiException extends RuntimeException {

    private static final long serialVersionUID = -5155234678817491729L;
    private String code;

    private HttpStatus httpStatus;
    
    private String reason;
    
    private Throwable cause;
    
    private Boolean isDownstreamException;
    
    private String component;
    
    private String request;
    
    private String url;
    
}
