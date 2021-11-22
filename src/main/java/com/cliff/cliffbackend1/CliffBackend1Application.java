package com.cliff.cliffbackend1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CliffBackend1Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(CliffBackend1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(CliffBackend1Application.class, args);
        LOGGER.info("The Spring boot application has started !!");
    }

}
