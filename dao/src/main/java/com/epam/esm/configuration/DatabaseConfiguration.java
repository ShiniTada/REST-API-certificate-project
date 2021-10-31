package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource("classpath:property/dataProperties.properties")
@AllArgsConstructor
public class DatabaseConfiguration {
    private final Environment ENVIRONMENT;
    private final String URL = "url";
    private final String DRIVER_CLASS_NAME = "driverClassName";
    private final String USER_NAME = "user";
    private final String PASSWORD = "password";
    private final String POOL_SIZE = "maximum-pool-size";

    @Bean
    @Profile("prod")
    public DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ENVIRONMENT.getProperty(URL));
        config.setUsername(ENVIRONMENT.getProperty(USER_NAME));
        config.setPassword(ENVIRONMENT.getProperty(PASSWORD));
        config.setDriverClassName(ENVIRONMENT.getProperty(DRIVER_CLASS_NAME));
        config.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(ENVIRONMENT.getProperty(POOL_SIZE))));
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
