package com.microcommerce.zuul_zuul_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ZuulZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulZuulServerApplication.class, args);
	}

}
