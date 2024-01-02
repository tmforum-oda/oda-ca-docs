package com.tmf.pim.iam.ext.utility;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tmf.pim.iam.ext.constants.IamExtConstants;

import javax.annotation.PostConstruct;

/**
 * Contains utility Functions to mask sensitive information
 */

@Component
public class UriAnonymizer {
    
    @Value("${apiUri.pattern}")
    String uriPattern;
    
    private String[] patterns = ArrayUtils.EMPTY_STRING_ARRAY;
    
    @PostConstruct
    public void init(){
        if(StringUtils.isNotBlank(uriPattern)){
            patterns = StringUtils.split(uriPattern, IamExtConstants.COMMA);
        }
    }
    
    public String maskIdentifier(String requestUri){
        if(!StringUtils.isBlank(requestUri)){
            for(String pattern: patterns){
                requestUri = RegExUtils.replacePattern(requestUri, pattern, "*");
            }
        }
        return requestUri;
    }
}
