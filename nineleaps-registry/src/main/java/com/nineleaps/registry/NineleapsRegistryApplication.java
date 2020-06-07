package com.nineleaps.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NineleapsRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(NineleapsRegistryApplication.class, args);
	}

}
