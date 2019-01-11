package com.wsct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
@SpringBootApplication
public class FirstApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class,args);
		System.out.println("-----------------------start---------------------");
	}
    protected SpringApplicationBuilder config(SpringApplicationBuilder applicationBuilder){
        return applicationBuilder.sources(FirstApplication.class);
    }
}
