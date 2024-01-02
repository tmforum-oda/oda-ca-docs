package com.tmf.tmf637.pimgateway.utility;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.tmf.pim.iam.ext.utility.ResilienceRegistry;

import java.net.URISyntaxException;

@RunWith(MockitoJUnitRunner.class)
public class ResilienceRegistryTest {
    
    @InjectMocks
    ResilienceRegistry resilienceRegistry;
    ResilienceRegistry resilienceRegistryConcrete;
    
    @Before
    public void setup() throws URISyntaxException {
        resilienceRegistryConcrete = new ResilienceRegistry();
        ReflectionTestUtils.setField(resilienceRegistry, "srEndpoint", "http://localhost:8081/cwf/sr/v1/product");
        ReflectionTestUtils.setField(resilienceRegistry, "iamEndpoint", "https://aws.iam/auth/realms/cpe/protocol/openid-connect/token");
    }

    @Test
    public  void initTest(){
        String registeryName = resilienceRegistry.getRegistryEntryFor("https://aws.iam/auth/realms/cpe/protocol/openid-connect/token");
        Assert.assertEquals("https://aws.iam/auth/realms/cpe/protocol/openid-connect/token", registeryName);
    }

    @Test
    public void registerEmptyNotEffectTest(){
        resilienceRegistry.register("service.com/ep");
        String registeryName = resilienceRegistry.getRegistryEntryFor( "http://localhost:8081/cwf/sr/v1/product");
        Assert.assertEquals("http://localhost:8081/cwf/sr/v1/product", registeryName);
    }

    @Test
    public void getRegistryEntryFromListTest(){
        resilienceRegistry.register("http://localhost:8081/cwf/sr/v1/product1");
        String registeryName = resilienceRegistry.getRegistryEntryFor("http://localhost:8081/cwf/sr/v1/product123");
        Assert.assertEquals("http://localhost:8081/cwf/sr/v1/product1", registeryName);
    }

    @Test
    public void getRegistryEntrySuccessTest(){
        String registeryName = resilienceRegistry.getRegistryEntryFor("http://localhost:8081/cwf/sr/v1/product1");
        Assert.assertEquals("http://localhost:8081/cwf/sr/v1/product1", registeryName);
    }
}
