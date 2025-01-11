package com.example.directoryservice.service;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.dto.PersonDto;

import java.util.List;

public interface PersonService {
    PersonDto createPerson(PersonDto personDto);
    PersonDto getPersonById(String id);
    PersonDto updatePersonContact(String personalId, ContactDto contactDto);
    void deletePersonById(String id);
    List<PersonDto> getAllPersons();
}
