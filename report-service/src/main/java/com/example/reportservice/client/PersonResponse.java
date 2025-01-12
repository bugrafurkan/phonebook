package com.example.reportservice.client;

import com.example.directoryservice.entity.Contact;

import java.util.List;

@lombok.Getter
@lombok.Setter
public class PersonResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String company;
    private List<Contact> contacts;

}
