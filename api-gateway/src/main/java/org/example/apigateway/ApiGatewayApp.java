package org.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ApiGatewayApp.class, args);
    }
}
