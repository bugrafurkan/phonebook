package com.example.directoryservice.service;

import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.entity.Person;
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

    @Test
    void testCreatePerson_success() {
        // Girdi olarak kullanılacak PersonDto nesnesi
        PersonDto requestDto = new PersonDto();
        requestDto.setFirstName("Murat");
        requestDto.setLastName("Kaya");
        requestDto.setCompany("Setur");

        // Mocklama sırasında döndürülecek Person nesnesi
        Person savedPerson = new Person();
        savedPerson.setId("12345");
        savedPerson.setFirstName("Murat");
        savedPerson.setLastName("Kaya");
        savedPerson.setCompany("Setur");

        // personRepository.save çağrıldığında savedPerson dönecek şekilde ayarlanıyor
        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);

        // Test edilen metodu çağırıyoruz
        PersonDto resultDto = personService.createPerson(requestDto);

        // Çıktı sonuçlarının doğruluğunu kontrol ediyoruz
        assertThat(resultDto).isNotNull(); // Dönüş null olmamalı
        assertThat(resultDto.getId()).isEqualTo("12345"); // ID kontrol
        assertThat(resultDto.getFirstName()).isEqualTo("Murat"); // İsim kontrol
        assertThat(resultDto.getLastName()).isEqualTo("Kaya"); // Soyisim kontrol
        assertThat(resultDto.getCompany()).isEqualTo("Setur"); // Şirket kontrol

        // personRepository.save'in çağrıldığını doğrula
        verify(personRepository).save(any(Person.class));
    }
}
