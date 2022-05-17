package com.pet.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.pet"})
@EntityScan("com.pet.models")
@EnableDiscoveryClient
//引入Spring Task
@EnableScheduling
public class PayMent {
    public static void main(String[] args)
    {
        SpringApplication.run(PayMent.class,args);
    }
}

