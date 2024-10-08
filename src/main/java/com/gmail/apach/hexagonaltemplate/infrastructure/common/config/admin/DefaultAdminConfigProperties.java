package com.gmail.apach.hexagonaltemplate.infrastructure.common.config.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.default-admin")
@Data
public class DefaultAdminConfigProperties {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
}
