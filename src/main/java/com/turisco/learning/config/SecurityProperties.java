package com.turisco.learning.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {
    private List<UserProperties> users;

    @Getter
    @Setter
    public static class UserProperties {
        private String username;
        private String password;
        private String role;
    }
}