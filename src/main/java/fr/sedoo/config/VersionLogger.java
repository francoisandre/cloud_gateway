package fr.sedoo.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class VersionLogger {
	
	
	@PostConstruct
	public void init() {
		log.info("Version A");
	}
}
