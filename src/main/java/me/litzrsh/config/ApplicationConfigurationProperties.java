package me.litzrsh.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class ApplicationConfigurationProperties {

    private String from;

    private String[] to = {};

    private String[] cc = {};

    private String host;

    private Integer port;

    private String username;

    private String password;
}
