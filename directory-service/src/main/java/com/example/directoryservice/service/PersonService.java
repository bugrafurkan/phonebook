package com.example.directoryservice.service;

import com.example.directoryservice.dto.PersonDto;

import java.util.List;

public interface PersonService {
    PersonDto createPerson(PersonDto personDto);
    PersonDto getPersonById(String id);
    PersonDto updatePerson(String personalId, PersonDto personDto);
    void deletePersonById(Long id);
    List<PersonDto> getAllPersons();
}
