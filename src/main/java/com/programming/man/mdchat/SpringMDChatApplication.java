package com.programming.man.mdchat;

import com.programming.man.mdchat.config.OpenAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(OpenAPIConfiguration.class)
public class SpringMDChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMDChatApplication.class, args);
    }

}
