package com.example.directoryservice.service;

import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.dto.DirectoryEvent;
import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.entity.ContactType;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.kafka.DirectoryEventsProducer;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceImplTest {

    private PersonServiceImpl personService;
    private PersonRepository personRepository;
    private ContactService contactService;
    private DirectoryEventsProducer.KafkaProducer kafkaProducer;

    @BeforeEach
    void setup() {
        personRepository = mock(PersonRepository.class);
        contactService = mock(ContactService.class);
        kafkaProducer = mock(DirectoryEventsProducer.KafkaProducer.class);

        personService = new PersonServiceImpl(personRepository, contactService, kafkaProducer);
    }

    @Test
    void createPerson_success() {
        PersonDto personDto = PersonDto.builder()
                .firstName("John")
                .lastName("Doe")
                .company("TechCorp")
                .build();

        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setCompany("TechCorp");

        // Mock repository save behavior
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto savedPersonDto = personService.createPerson(personDto);

        // Verify save method called
        verify(personRepository, times(1)).save(any(Person.class));
        verify(kafkaProducer, times(1)).sendPersonEvent(any(DirectoryEvent.class));

        // Validate returned DTO

        assertEquals(person.getFirstName(), savedPersonDto.getFirstName());
        assertEquals(person.getLastName(), savedPersonDto.getLastName());
    }

    @Test
    void createPerson_nullPerson_throwsException() {
        PersonDto personDto = PersonDto.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        when(personRepository.save(any(Person.class))).thenReturn(null);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                personService.createPerson(personDto));

        assertEquals("Saved person is null!", exception.getMessage());
    }

    @Test
    void getPersonById_successful() {
        String id = UUID.randomUUID().toString();
        Person person = new Person();
        person.setId(id);
        person.setFirstName("Jane");
        person.setLastName("Doe");

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        // Act
        PersonDto personDto = personService.getPersonById(id);

        // Assert
        assertNotNull(personDto);
        assertEquals(id, personDto.getId());
        assertEquals("Jane", personDto.getFirstName());
        assertEquals("Doe", personDto.getLastName());
    }

    @Test
    void getPersonById_notFound_throwsException() {
        String id = UUID.randomUUID().toString();

        when(personRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                personService.getPersonById(id));

        assertEquals("Person not found", exception.getMessage());
    }

    @Test
    void updatePersonContact_updatesContact() {
        String personId = UUID.randomUUID().toString();
        ContactDto contactDto = new ContactDto();
        contactDto.setId(1L);
        contactDto.setContactType(ContactType.valueOf("EMAIL"));
        contactDto.setContactDetail("alice@example.com");
        contactDto.setLocation("Istanbul");

        PersonDto personDto = PersonDto.builder()
                .id(personId)
                .firstName("Alice")
                .lastName("Smith")
                .contacts(new ArrayList<>())
                .build();

        when(personRepository.findById(personId))
                .thenReturn(Optional.of(personService.fromDto(personDto)));

        PersonDto updatedPersonDto = personService.updatePersonContact(personId, contactDto);

        assertNotNull(updatedPersonDto);
        assertEquals(1, updatedPersonDto.getContacts().size());
        //assertEquals("EMAIL", updatedPersonDto.getContacts().get(0).getContactType());
    }

    @Test
    void deletePersonById_deletesSuccessfully() {
        String id = UUID.randomUUID().toString();

        doNothing().when(personRepository).deleteById(id);

        // Act
        personService.deletePersonById(id);

        // Assert
        verify(personRepository, times(1)).deleteById(id);
    }

    @Test
    void getAllPersons_successful() {
        List<Person> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setId(UUID.randomUUID().toString());
        person1.setFirstName("Alice");
        Person person2 = new Person();
        person2.setId(UUID.randomUUID().toString());
        person2.setFirstName("Bob");
        persons.add(person1);
        persons.add(person2);

        when(personRepository.findAll()).thenReturn(persons);

        List<PersonDto> personList = personService.getAllPersons();

        assertNotNull(personList);
        assertEquals(2, personList.size());
        assertEquals("Alice", personList.get(0).getFirstName());
        assertEquals("Bob", personList.get(1).getFirstName());
    }
}