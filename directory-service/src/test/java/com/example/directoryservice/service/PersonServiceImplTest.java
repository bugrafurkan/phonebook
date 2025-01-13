package com.example.directoryservice.service;

import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.entity.Person;
import com.example.directoryservice.kafka.DirectoryEventsProducer;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {
    @Mock
    private PersonRepository personRepository;

    @Mock
    private DirectoryEventsProducer.KafkaProducer kafkaProducer;

    @InjectMocks
    private PersonServiceImpl personService;

    @BeforeEach
    void setUp() {
        try (AutoCloseable mocks = MockitoAnnotations.openMocks(this)) {
            // Mock nesnelerini başlat
        } catch (Exception e) {
            throw new RuntimeException(e); // Eğer bir hata oluşursa
        }
    }

   /** @Test
    void testCreatePerson_success() {
        // Girdi olarak kullanılacak PersonDto nesnesi
        PersonDto requestDto = new PersonDto();
        requestDto.setFirstName("Murat");
        requestDto.setLastName("Kaya");
        requestDto.setCompany("Setur");

        Person personToSave = new Person();
        personToSave.setFirstName("Murat");
        personToSave.setLastName("Kaya");
        personToSave.setCompany("Setur");

        // Repository'den dönen nesne (veritabanı tarafından atanmış ID ile)
        Person savedPerson = new Person();
        savedPerson.setId("12345");  // Teste uygun bir ID atayın
        savedPerson.setFirstName("Murat");
        savedPerson.setLastName("Kaya");
        savedPerson.setCompany("Setur");
        System.out.println("Saved person: " + savedPerson);
        System.out.println("Saved person ID: " + savedPerson.getId());

        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        PersonDto resultDto = personService.createPerson(requestDto);

        assertThat(resultDto).isNotNull(); // Dönüş null olmamalı
        assertThat(resultDto.getFirstName()).isEqualTo("Murat");
        assertThat(resultDto.getLastName()).isEqualTo("Kaya");
        assertThat(resultDto.getCompany()).isEqualTo("Setur");

        verify(personRepository).save(any(Person.class));
    }*/
}
