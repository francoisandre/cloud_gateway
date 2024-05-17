package fr.sedoo.config;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
/**
 * This class is used to log headers espiacilly for CORS issues... just uncomment component annotation et adapte to the project url to filter
 * @author fandre
 *
 */
public class LoggingFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
        String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
        if (originalUri.toLowerCase().contains("sedooaeris-catalogue-prod")) {
        		log.info("A logged request has arrived: "+originalUri);
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    String requestUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
                    String responseHeaders = exchange.getResponse().getHeaders().toSingleValueMap().toString();
                    log.info(requestUri+" --> "+responseHeaders);
                }));
        }  else {
        	return chain.filter(exchange);
        }
        
    }
}