package com.zdjavapol110.eventmanager.core.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ApplicationConfig {

    @Value("${domain}")
    private String domain;
    @Value("${email}")
    private String email;
    @Value("${user}")
    private String user;


    public ApplicationConfigDto asDto() {
        return new ApplicationConfigDto(domain, email, user);
    }
}

