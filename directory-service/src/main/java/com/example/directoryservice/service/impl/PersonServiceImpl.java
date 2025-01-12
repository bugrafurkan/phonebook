package com.example.directoryservice.service.impl;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.ContactService;
import com.example.directoryservice.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;

    private final ContactService contactService;

    public PersonServiceImpl(PersonRepository personRepository, ContactService contactService) {
        this.personRepository = personRepository;
        this.contactService = contactService;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = mapToEntity(personDto);
        System.out.println("Saving person: " + person.getId());
        personRepository.save(person);
        //System.out.println("Saved person: " + savedPerson);
        if (person == null) {
            throw new IllegalStateException("Saved person is null!");
        }

        return toPersonDto(person);
    }

    private Person mapToEntity(PersonDto personDto) {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setCompany(personDto.getCompany());
        if (personDto.getContacts() != null) {
            List<Contact> contacts = mapContacts(personDto.getContacts(), person);
            person.setConstacts(contacts);
        }
        return person;
    }

    private List<Contact> mapContacts(List<Contact> contactDtos, Person person) {
        return contactDtos.stream().map(contactDto -> {
            Contact contact = new Contact();
            contact.setId(contactDto.getId());
            contact.setContactType(contactDto.getContactType());
            contact.setPerson(person);
            return contact;
        }).toList();
    }

    @Override
    public PersonDto getPersonById(String id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return toPersonDto(person);
    }

    @Override
    public PersonDto updatePersonContact(String personalId, ContactDto contactDto) {
        PersonDto personDto = getPersonById(personalId);
        if (personDto != null) {
            Contact contact = new Contact();
            contact.setId(contactDto.getId());
            contact.setContactType(contactDto.getContactType());
            //person durumuna göre karar verelim
            contact.setPerson(fromDto(personDto));
            if (personDto.getContacts() != null) {
                personDto.getContacts().add(contact);
            }else {
                personDto.setContacts(List.of(contact));
            }
        }
        return personDto;
    }

    @Override
    public void deletePersonById(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<PersonDto> getAllPersons() {
        List<Person> people = personRepository.findAll();
        return people.stream()
                .map(this::toPersonDto)
                .toList();
    }

    private PersonDto toPersonDto(Person person) {
        if (Objects.isNull(person)) {
            throw new IllegalArgumentException("Person must not be null.");
        }
        return PersonDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .company(person.getCompany())
                .contacts(person.getConstacts())
                .build();
    }

    private Person fromDto(PersonDto personDto) {
        Person person = new Person();

        if (personDto.getId() != null) {
            person.setId(personDto.getId());
        }

        person.setFirstName(personDto.getFirstName());
        person.setLastName(personDto.getLastName());
        person.setCompany(personDto.getCompany());

        if (personDto.getContacts() != null) {
            List<Contact> contacts = mapContacts(personDto.getContacts(), person);
            person.setConstacts(contacts);
        }

        return person;
    }
}
