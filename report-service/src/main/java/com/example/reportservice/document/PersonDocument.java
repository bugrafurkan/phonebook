package com.example.reportservice.document;

import com.example.directoryservice.entity.Contact;
import jakarta.persistence.Id;

import java.util.List;
@lombok.Getter
@lombok.Setter
public class PersonDocument {
    @Id
    private String id;
    private String eventType;    // "PERSON_CREATED", "PERSON_UPDATED"
    private String firstName;
    private String lastName;
    private String company;
    private List<Contact> contacts;
}
