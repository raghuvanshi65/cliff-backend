package com.cliff.cliffbackend1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration Class that helps with application properties
 * */
@Configuration
@EnableTransactionManagement
public class CliffBackendConfig {

    @Autowired
    private static Environment environment;

    public static Environment getEnvironment(){
        return environment;
    }
}
