package com.example.directoryservice.service;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.entity.Contact;

public interface ContactService {
    Contact createContact(ContactDto contactDto);

    ContactDto toContactDto(Contact contact);

    void deleteContact(Long contactId);
}
