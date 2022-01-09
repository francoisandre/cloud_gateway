package fr.sedoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

import com.github.lalyos.jfiglet.FigletFont;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
@EnableAutoConfiguration
@ComponentScan(basePackages = { "fr.sedoo" })
public class APIGatewayApplication {

	public static void main(String[] args) {
		log.info("\n"+FigletFont.convertOneLine("<<< Gateway >>>"));
		SpringApplication.run(APIGatewayApplication.class, args);
	}

}