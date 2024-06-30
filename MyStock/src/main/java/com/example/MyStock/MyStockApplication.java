package com.example.MyStock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Rice Bags Api's",version = "1.0",description = "This Api Is to Rice  Bags"),
                          servers = @Server(url = "http://localhost:8080/api",description = "This is the Server where our Apis are Deployed"))
public class MyStockApplication {

	public static void main(String[] args) {
	  	SpringApplication.run(MyStockApplication.class, args);
	}

}
