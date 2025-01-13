package com.example.directoryservice.dto;

import com.example.directoryservice.entity.Contact;

import java.util.List;

@lombok.Getter
@lombok.Setter
public class DirectoryEvent {
    private String eventType;    // "PERSON_CREATED", "PERSON_UPDATED"
    private String personId;
    private String firstName;
    private String lastName;
    private String company;
    private List<Contact> contacts;
    private List<PhonebookEntryDto> phonebookEntries;
}
