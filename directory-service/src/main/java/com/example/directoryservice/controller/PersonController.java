package com.example.directoryservice.controller;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.service.PersonService;
import com.example.directoryservice.service.PhonebookEntryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private static final String BASE_URL = "/api/persons/";
    private static final String ID_PARAM = "/{id}";
    private final PersonService personService;
    private final PhonebookEntryService phonebookEntryService;

    public PersonController(PersonService personService, PhonebookEntryService phonebookEntryService) {
        this.personService = personService;
        this.phonebookEntryService = phonebookEntryService;
    }

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody PersonDto personDto) {
        PersonDto newPersonDto = personService.createPerson(personDto);
        String createdPersonId = newPersonDto.getId();
        URI location = URI.create(BASE_URL  + createdPersonId);
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping(ID_PARAM)
    public ResponseEntity<Void> deletePersonById(@PathVariable String id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<PersonDto> allPersons = personService.getAllPersons();
        return ResponseEntity.ok(allPersons);
    }

    @GetMapping(ID_PARAM)
    public ResponseEntity<PersonDto> getPersonById(@PathVariable String id) {
        PersonDto personDto = personService.getPersonById(id);
        if (Objects.isNull(personDto)) {
            return ResponseEntity.notFound().build();
        }
        //servis içinde unutma
        /**
         * // “Manuel olarak phonebookEntry bilgilerini de doldurmak” = Service içinde de yapabilirsiniz.
         *         // Ama diyelim ki bunu phonebookEntryService’de ayrı metodla çekmek istiyorsunuz:
         *         List<PhonebookEntryDto> entryDtos = phonebookEntryService.findEntriesOfOwner(personId);
         *         personDto.setPhonebookEntries(entryDtos);
         * */
        return ResponseEntity.ok(personDto);
    }

    @PutMapping
    public ResponseEntity<PersonDto> updatePerson(@RequestParam("personalId") String personalId,
                                                  @RequestBody ContactDto contactDto) {
        PersonDto updatedPersonDto = personService.updatePersonContact(personalId,contactDto);
        return ResponseEntity.ok(updatedPersonDto);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getPersonsWithContacts(){
        List<Person> personList = personService.getAllPersonsWithContacts();
        List<PersonDto> personDtoList = personService.toPersonDtoliST(personList);
        return ResponseEntity.ok(personDtoList);
    }

}
