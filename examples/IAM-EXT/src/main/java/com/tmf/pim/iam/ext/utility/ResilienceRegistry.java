package com.tmf.pim.iam.ext.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ResilienceRegistry {
    
    private List<String> registryUriList = new ArrayList<>();
    public static final Logger TMF637_Adapter_Logger = LoggerFactory.getLogger(ResilienceRegistry.class);

    public void register(String uri) {
        
        if(null == uri || uri.isEmpty()){
            TMF637_Adapter_Logger.warn("uri is empty. Cannot be registered");
            return;
        }
        if(registryUriList.contains(uri)){
            return;
        }
        registryUriList.add(uri);
    }

    public String getRegistryEntryFor(String uri){
        TMF637_Adapter_Logger.debug("getRegisteryEntryFor : {} ", uri);
        Optional<String> circuitBreakerName = registryUriList.stream().filter(
                uriPattern -> {
                    return uri.contains(uriPattern);
                }
        ).max((firstUri, secondUri) -> firstUri.length() - secondUri.length());
        
        return circuitBreakerName.orElse(uri);
    }
    
}
