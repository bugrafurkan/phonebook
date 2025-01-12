package com.example.directoryservice.dto;

import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.entity.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class PersonDto {
    private String id;
    private String firstName;
    private String lastName;
    private String company;
    private List<Contact> contacts;
    private List<PhonebookEntryDto> phonebookEntries;
    

    public PersonDto(String id, String firstName, String lastName, String company, List<Contact> contacts, List<PhonebookEntryDto> phonebookEntries) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.contacts = contacts;
        this.phonebookEntries = phonebookEntries;
    }

    public PersonDto() {

    }

}
