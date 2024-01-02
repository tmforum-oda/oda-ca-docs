package com.tmf.pim.iam.ext.metrics;

import lombok.Data;

@Data
public class ExternalSystemMetricsEntity {
    
    private Long elapsedTime;
    private String uri;
    private String statusCode;
    private String externalSystem;
    private String httpMethod;
}
