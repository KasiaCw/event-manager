package com.zdjavapol110.eventmanager.core.config;

public class ApplicationConfigDto {

    private final String domain;
    private final String email;
    private final String user;


    public ApplicationConfigDto(String domain, String email, String user) {
        this.domain = domain;
        this.email = email;
        this.user = user;
    }

    public String getDomain() {
        return domain;
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return user;
    }
}
