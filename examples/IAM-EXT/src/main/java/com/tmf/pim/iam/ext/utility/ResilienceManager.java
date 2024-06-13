package com.tmf.pim.iam.ext.utility;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.vavr.CheckedFunction0;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Duration;

@Service
public class ResilienceManager {

    public static final int CIRCUIT_BRAKER_FAILURE_CALL_RATE_THRESHOLD = 50;
    public static final int CIRCUIT_BRAKER_SLOW_CALL_RATE_THRESHOLD = 50;
    public static final int CIRCUIT_BRAKER_PERMITTED_NO_CALLS_IN_HALFOPEN = 3;

    public static final Logger TMF637_Adapter_Logger = LoggerFactory.getLogger(ResilienceManager.class);
    
    @Autowired
    ResilienceRegistry resilienceRegistry;

    @Value("${resilience.retry.maxretrythreshold}")
    private int retryMaxRetryThreshold;

    @Value("${resilience.retry.retrytimeinterval}")
    private int retryMaxRetryInterval;

    @Value("${resilience.circuit.waitdurationinopenstate}")
    private int circuitWaitDurationInOpenState;

    @Value("${resilience.circuit.minimumnumberofcalls}")
    private int circuitMinimumNumberOfCalls;

    @Value("${resilience.circuit.slidingwindowsize}")
    private int circuitSlidingWindowSize;
    
    private CircuitBreakerRegistry circuitBreakerRegistry;
    private RetryRegistry retryRegistry;
    private int activeRetryMaxRetryInterval;
    private int activeRetryMaxRetryThreshold;
    
    @PostConstruct
    public void init() {
        createCircuitBreakerFactory();
        createRetryFactory();
    }

    private void createRetryFactory() {
        int threshold = activeRetryMaxRetryThreshold == 0 ? 1: activeRetryMaxRetryThreshold;
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(threshold)
                .waitDuration(Duration.ofMillis(activeRetryMaxRetryInterval))
                .retryExceptions(IOException.class)
                .build();
        retryRegistry = RetryRegistry.of(config);
    }

    private void createCircuitBreakerFactory(){
        int slidingWindowSize = circuitSlidingWindowSize == 0 ? 1 : circuitSlidingWindowSize;
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(CIRCUIT_BRAKER_FAILURE_CALL_RATE_THRESHOLD)
                .slowCallRateThreshold(CIRCUIT_BRAKER_SLOW_CALL_RATE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofMillis(circuitWaitDurationInOpenState))
                .permittedNumberOfCallsInHalfOpenState(CIRCUIT_BRAKER_PERMITTED_NO_CALLS_IN_HALFOPEN)
                .minimumNumberOfCalls(circuitMinimumNumberOfCalls)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(slidingWindowSize)
                .recordExceptions(IOException.class)
                .build();
        circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
    }

    private void checkForChange(){
        int newRequestMaxRetryThreshold = CacheStore.getInstance().getResilienceReqeustRetryThreshold() != null
                ? CacheStore.getInstance().getResilienceReqeustRetryThreshold() : retryMaxRetryThreshold;
        int newRequestMaxRetryInterval = CacheStore.getInstance().getResilienceReqeustRetryTimeInterval() != null
                ? CacheStore.getInstance().getResilienceReqeustRetryTimeInterval() : retryMaxRetryInterval;
        if(newRequestMaxRetryThreshold != activeRetryMaxRetryThreshold
                || newRequestMaxRetryInterval != activeRetryMaxRetryInterval ){
            activeRetryMaxRetryInterval = newRequestMaxRetryInterval;
            activeRetryMaxRetryThreshold = newRequestMaxRetryThreshold;
            createRetryFactory();
        }
    }

    public CircuitBreaker getCircuitBreaker(String uri){
        String circuitName = resilienceRegistry.getRegistryEntryFor(uri);
        TMF637_Adapter_Logger.debug("is reqeusted - best match - " + circuitName );
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker( circuitName );
        if(circuitSlidingWindowSize == 0){
            if( circuitBreaker.getState() !=  CircuitBreaker.State.DISABLED ){
                circuitBreaker.transitionToDisabledState();
            }
        }else if( circuitBreaker.getState() ==  CircuitBreaker.State.DISABLED ){
            circuitBreaker.transitionToClosedState();
        }
        return circuitBreaker;
    }

    public Retry getRetry(String uri){
        String retryName = resilienceRegistry.getRegistryEntryFor(uri);
        TMF637_Adapter_Logger.debug("is reqeusted - best match - " + retryName );
        Retry retry = retryRegistry.retry(retryName);
        return retry;
    }

    public CheckedFunction0<ClientHttpResponse> getCaller(CheckedFunction0<ClientHttpResponse> supplier, String uri){
        checkForChange();
        CheckedFunction0<ClientHttpResponse> decoratedSupplier = Decorators
                .ofCheckedSupplier(supplier)
                .withCircuitBreaker( getCircuitBreaker(uri))
                .withRetry( getRetry( uri) )
                .decorate();
        return decoratedSupplier;
    }
}
