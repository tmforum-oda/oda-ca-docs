package com.tmf.pim.iam.ext.utility;

import lombok.Getter;
import lombok.Setter;

public class CacheStore {
    
    private CacheStore(){}

    private static volatile CacheStore cacheStore;
    
    public static synchronized CacheStore getInstance()
    {
        if (cacheStore == null)
        {
            synchronized (CacheStore.class)
            {
                if (cacheStore == null)
                {
                    cacheStore = new CacheStore();
                }
            }
        }
        return cacheStore;
    }

    @Getter
    @Setter
    private Integer resilienceRequestTimeout;

    @Getter
    @Setter
    private Integer resilienceReqeustRetryThreshold;

    @Getter
    @Setter
    private Integer resilienceReqeustRetryTimeInterval;
    
}
