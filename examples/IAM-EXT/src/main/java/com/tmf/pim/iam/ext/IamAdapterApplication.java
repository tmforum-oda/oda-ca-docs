package com.tmf.pim.iam.ext;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

import com.tmf.pim.iam.ext.config.IamEndpointExposureConfiguration;

@Configuration
@EnableAsync
@EnableRetry
@SpringBootApplication(exclude = {
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class
})
@OpenAPIDefinition(info = @Info(title = "ODA PIM Gateway - IAM Adapter", version = "1.0", description = "Ericsson - Documentation APIs v1.0"))
public class IamAdapterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(IamAdapterApplication.class, args);
	}
}
