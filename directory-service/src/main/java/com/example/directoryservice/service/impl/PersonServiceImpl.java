package com.example.directoryservice.service.impl;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.dto.DirectoryEvent;
import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.kafka.DirectoryEventsProducer;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.ContactService;
import com.example.directoryservice.service.PersonService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {


    private final PersonRepository personRepository;
    private final ContactService contactService;
    private final DirectoryEventsProducer.KafkaProducer kafkaProducer;

    public PersonServiceImpl(PersonRepository personRepository, ContactService contactService, DirectoryEventsProducer.KafkaProducer kafkaProducer) {
        this.personRepository = personRepository;
        this.contactService = contactService;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {
        Person person = mapToEntity(personDto);
        System.out.println("Saving person: " + person.getId());
        //Person savedPerson = personRepository.save(person);
        personRepository.save(person);
        if (Objects.isNull(person)) {
            throw new IllegalStateException("Saved person is null!");
        }
        DirectoryEvent event = new DirectoryEvent();
        event.setEventType("PERSON_CREATE");
        event.setPersonId(person.getId());
        event.setFirstName(person.getFirstName());
        event.setLastName(person.getLastName());
        event.setCompany(person.getCompany());
        event.setContacts(person.getConstacts());
        kafkaProducer.sendPersonEvent(event);
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

    @Override
    public List<Person> getAllPersonsWithContacts() {
        return personRepository.findAll();
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

    @Override
    public List<PersonDto> toPersonDtoliST(List<Person> personList) {
        List<PersonDto> personDtoList = new ArrayList<>();
        for(Person person : personList){
            if (Objects.isNull(person)) {
                throw new IllegalArgumentException("Person must not be null.");
            }
            PersonDto personDto = new PersonDto();
            personDto.setId(person.getId());
            personDto.setFirstName(person.getFirstName());
            personDto.setLastName(person.getLastName());
            personDto.setCompany(person.getCompany());
            personDto.setContacts(person.getConstacts());
            personDtoList.add(personDto);
        }


        return personDtoList;
    }
}
