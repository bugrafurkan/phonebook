package com.example.reportservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "directory-service", url = "http://localhost:8081")
public interface DirectoryFeignClient {

    @GetMapping("/api/persons")
    List<PersonResponse> getAllPersons();

    @GetMapping("/api/persons/with-contacts")
    List<PersonResponse> getPersonsWithContacts();

}

