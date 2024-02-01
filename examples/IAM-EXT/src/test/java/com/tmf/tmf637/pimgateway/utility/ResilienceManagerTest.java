package com.tmf.tmf637.pimgateway.utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.tmf.pim.iam.ext.utility.ResilienceManager;
import com.tmf.pim.iam.ext.utility.ResilienceRegistry;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import io.vavr.CheckedFunction0;

@RunWith(MockitoJUnitRunner.class)
public class ResilienceManagerTest {

    @Mock
    ResilienceRegistry resilienceRegistry;
    
    private void setResilienceValues(ResilienceManager resilienceManager){
        ReflectionTestUtils.setField(resilienceManager, "retryMaxRetryThreshold", 10);
        ReflectionTestUtils.setField(resilienceManager, "retryMaxRetryInterval", 10);
        ReflectionTestUtils.setField(resilienceManager, "circuitWaitDurationInOpenState", 10);
        ReflectionTestUtils.setField(resilienceManager, "circuitMinimumNumberOfCalls", 10);
        ReflectionTestUtils.setField(resilienceManager, "circuitSlidingWindowSize", 10);
        ReflectionTestUtils.setField(resilienceManager, "resilienceRegistry", resilienceRegistry);
        resilienceManager.init();
    }

    @Test
    public void getCircuitBreakerWithMatchTest(){
        Mockito.when(resilienceRegistry.getRegistryEntryFor(Mockito.any())).thenReturn("http://some.url/some/service/product_");
        ResilienceManager resilienceManager = new ResilienceManager();
        setResilienceValues(resilienceManager);
        Retry retry = resilienceManager.getRetry("http://some.url/some/service/product_9238293");
        Assert.assertEquals("http://some.url/some/service/product_", retry.getName());
    }

    @Test
    public void getCircuitBreakerWithoutMatchTest(){
        Mockito.when(resilienceRegistry.getRegistryEntryFor(Mockito.any())).thenReturn("http://some.url/some/service/product_9238293");
        ResilienceManager resilienceManager = new ResilienceManager();
        setResilienceValues(resilienceManager);
        Retry retry = resilienceManager.getRetry("http://some.url/some/service/product_9238293");
        Assert.assertEquals("http://some.url/some/service/product_9238293", retry.getName());
    }

    @Test
    public void getRetryWithMatchTest(){
        Mockito.when(resilienceRegistry.getRegistryEntryFor(Mockito.any())).thenReturn("http://some.url/some/service/product_");
        ResilienceManager resilienceManager = new ResilienceManager();
        setResilienceValues(resilienceManager);
        CircuitBreaker circuitBreaker = resilienceManager.getCircuitBreaker("http://some.url/some/service/product_9238293?key=somekey");
        Assert.assertEquals("http://some.url/some/service/product_", circuitBreaker.getName());
    }

    @Test(expected = Exception.class)
    public void getCallerTest() throws Throwable {
        ResilienceManager resilienceManager = new ResilienceManager();
        setResilienceValues(resilienceManager);
        CheckedFunction0 checkedFunction = resilienceManager.getCaller(()->{throw new Exception();}, "b");
        checkedFunction.apply();
    }
}
