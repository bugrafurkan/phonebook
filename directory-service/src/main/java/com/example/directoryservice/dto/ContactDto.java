package com.example.directoryservice.dto;

import com.example.directoryservice.entity.ContactType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {
    private Long id;
    private ContactType contactType;
    private String contactDetail;
    private String personId;

}

