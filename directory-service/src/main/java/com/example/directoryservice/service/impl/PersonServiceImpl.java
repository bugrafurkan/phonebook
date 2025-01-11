package com.example.directoryservice.service.impl;

import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.service.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {
    @Override
    public PersonDto createPerson(PersonDto personDto) {
        return null;
    }

    @Override
    public PersonDto getPersonById(String id) {
        return null;
    }

    @Override
    public PersonDto updatePerson(String personalId, PersonDto personDto) {
        return null;
    }

    @Override
    public void deletePersonById(Long id) {

    }

    @Override
    public List<PersonDto> getAllPersons() {
        return List.of();
    }
}
