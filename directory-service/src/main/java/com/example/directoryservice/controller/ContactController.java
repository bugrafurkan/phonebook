package com.example.directoryservice.controller;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.ContactService;
import com.example.directoryservice.service.impl.PersonServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {
    private final ContactService contactService;
    private final PersonRepository personRepository;
    private static final String BASE_URL = "/api/contacts/";
    private static final String ID_PARAM = "/{id}";

    public ContactController(ContactService contactService, PersonRepository personRepository) {
        this.contactService = contactService;
        this.personRepository = personRepository;
    }

    @PostMapping
    public ResponseEntity<Void> addContact(@RequestBody ContactDto contactDto) {
        String personId = contactDto.getPersonId();
        if (personId == null || personId.isBlank() || !personRepository.existsById(personId) ) {
            return ResponseEntity.badRequest().build();
        }

        Contact contact = contactService.createContact(contactDto);
        String createdContactId = contact.getId().toString();
        URI location = URI.create(BASE_URL  + createdContactId);
        //ContactDto responseDto = contactService.toContactDto(contact);
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(ID_PARAM)
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
