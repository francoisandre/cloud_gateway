package fr.sedoo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


@Slf4j
@Configuration
public class CorsConfiguration {

  private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN";
  private static final String ALLOWED_METHODS = "OPTIONS, GET, PUT, POST, DELETE, PATCH";
  private static final String MAX_AGE = "3600";

  @Bean
  public WebFilter corsFilter() {
	  log.info("Gateway Cors Filter...");
    return (ServerWebExchange ctx, WebFilterChain chain) -> {
      ServerHttpRequest request = ctx.getRequest();
      if (request.getHeaders().containsKey(HttpHeaders.ORIGIN)) {
        ServerHttpResponse response = ctx.getResponse();
        HttpHeaders headers = response.getHeaders();
        
        if (request.getHeaders().getOrigin().toLowerCase().indexOf("localhost")<0) {
        	headers.setAccessControlAllowOrigin(request.getHeaders().getOrigin());
        } else {
        	//If request comes from localhost, we must put "*" as origin
        	if (request.getMethod() == HttpMethod.OPTIONS) {
        		headers.setAccessControlAllowOrigin("*");
        	}
        }
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Max-Age", MAX_AGE);
        headers.add("Access-Control-Allow-Headers",ALLOWED_HEADERS);
        
        if (request.getMethod() == HttpMethod.OPTIONS) {
          response.setStatusCode(HttpStatus.OK);
          return Mono.empty();
        }
      }
      return chain.filter(ctx);
    };
  }

}