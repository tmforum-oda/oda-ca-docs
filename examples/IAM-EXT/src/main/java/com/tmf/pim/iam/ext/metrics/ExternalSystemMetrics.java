package com.tmf.pim.iam.ext.metrics;

import com.tmf.pim.iam.ext.constants.IamExtConstants;
import com.tmf.pim.iam.ext.utility.UriAnonymizer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@Component
public class ExternalSystemMetrics {
    
    @Setter
    @Autowired
    private MeterRegistry meterRegistry;
    
    @Setter
    @Autowired
    private UriAnonymizer uriAnonymizer;
    
    public void createExternalMetrics(ExternalSystemMetricsEntity externalSystemMetricsEntity){
        
        if(externalSystemMetricsEntity != null){
            
            Timer timerBuilder = Timer.builder(IamExtConstants.EXTERNAL_SYSTEM_INTERACTION_REQUEST)
                    .description("indicates total response time of the service")
                    .tags(IamExtConstants.HOST, getHostName())
                    .tags(IamExtConstants.SOURCE, "ODA-TMF637")
                    .tags(IamExtConstants.STATUS, externalSystemMetricsEntity.getStatusCode())
                    .tags(IamExtConstants.URI, uriAnonymizer.maskIdentifier(externalSystemMetricsEntity.getUri()))
                    .tags(IamExtConstants.SYSTEM, externalSystemMetricsEntity.getExternalSystem())
                    .tags(IamExtConstants.HTTP_METHOD, externalSystemMetricsEntity.getHttpMethod())
                    .register(meterRegistry);
            if(timerBuilder != null ){
                timerBuilder.record(externalSystemMetricsEntity.getElapsedTime(), TimeUnit.MILLISECONDS);
            }
        }
        
    }

    private String getHostName(){
        String hostName = null;
        try{
            hostName = System.getenv(IamExtConstants.NODE_NAME);
            if(StringUtils.isBlank(hostName)){
                hostName = InetAddress.getLocalHost().getHostName();
            }
        }catch(UnknownHostException exception){
            hostName = StringUtils.EMPTY;
        }
         return hostName;   
    }
}
