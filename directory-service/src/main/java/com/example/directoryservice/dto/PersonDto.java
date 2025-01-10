package com.example.directoryservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class PersonDto {
    private String id;
    private String firstName;
    private String lastName;
    private String company;
    private List<ContactDto> contacts;
    private List<PhonebookEntryDto> phonebookEntries;

}
