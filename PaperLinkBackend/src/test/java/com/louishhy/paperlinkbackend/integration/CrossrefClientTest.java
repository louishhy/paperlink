package com.louishhy.paperlinkbackend.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.louishhy.paperlinkbackend.dto.crossref.CrossrefWork;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class CrossrefClientTest {
    public static void main(String[] args) {
        CrossrefClient client = new CrossrefClient();
        try {
            java.lang.reflect.Field emailField = CrossrefClient.class.getDeclaredField("email");
            emailField.setAccessible(true);
            emailField.set(client, "breezelo2000@gmail.com");

            String testDoi = "10.1145/3359591.3359737";
            CrossrefWork work = client.getWorkByDoi(testDoi);

            System.out.println("Title: " + work.getTitle());
            System.out.println("DOI: " + work.getDoi());
            System.out.println("Authors: " + work.getAuthor());
            System.out.println("Abstract: " + work.getAbstract_());
            System.out.println("URL: " + work.getUrl());
            System.out.println("Container title: " + work.getContainerTitle());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}