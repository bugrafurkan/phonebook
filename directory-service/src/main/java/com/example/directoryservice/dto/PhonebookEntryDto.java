package com.example.directoryservice.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PhonebookEntryDto {
    private Long id;
    private String ownerId;
    private String contactId;
    private PersonDto contactPerson;

}
