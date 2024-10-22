package com.louishhy.paperlinkbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PaperLinkBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaperLinkBackendApplication.class, args);
    }

}
