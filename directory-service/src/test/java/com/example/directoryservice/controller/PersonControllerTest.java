package com.example.directoryservice.controller;

import com.example.directoryservice.DirectoryServiceApplication;
import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.service.ContactService;
import com.example.directoryservice.service.PersonService;
import com.example.directoryservice.service.PhonebookEntryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@ContextConfiguration(classes = DirectoryServiceApplication.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @MockBean
    private PhonebookEntryService phonebookEntryService;

    @MockBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/persons/";

    @Test
    void createPerson_returnOk() throws Exception {
        PersonDto requestDto = new PersonDto();
        requestDto.setFirstName("Murat");
        requestDto.setLastName("Kaya");
        requestDto.setCompany("Setur");

        PersonDto responseDto = new PersonDto();
        responseDto.setId("12345");
        responseDto.setFirstName("Murat");
        responseDto.setLastName("Kaya");
        responseDto.setCompany("Setur");

        Mockito.when(personService.createPerson(any(PersonDto.class)))
                .thenReturn(responseDto);
        String baseUrl = BASE_URL + "12345";
        mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",baseUrl));

    }

    @Test
    void getPersonById_shouldReturnNotFound() throws Exception {
        Mockito.when(personService.getPersonById("9999")).thenReturn(null);

        mockMvc.perform(get("/api/persons/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePersonById_returnNoContent() throws Exception {
        Mockito.doNothing().when(personService).deletePersonById("12345");

        mockMvc.perform(delete("/api/persons/{id}", "12345"))
                .andExpect(status().isNoContent()); // 204 No Content check
    }

    @Test
    void getAllPersons_returnListOfPersons() throws Exception {
        // Mock data
        List<PersonDto> personList = Arrays.asList(
                new PersonDto("12345", "Murat", "Kaya", "Setur",null,null),
                new PersonDto("67890", "Ali", "Veli", "Arçelik",null,null)
        );

        Mockito.when(personService.getAllPersons()).thenReturn(personList);

        mockMvc.perform(get("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // 200 Status check
                .andExpect(jsonPath("$[0].id").value("12345"))
                .andExpect(jsonPath("$[0].firstName").value("Murat"))
                .andExpect(jsonPath("$[0].lastName").value("Kaya"))
                .andExpect(jsonPath("$[0].company").value("Setur"))
                .andExpect(jsonPath("$[1].id").value("67890"))
                .andExpect(jsonPath("$[1].firstName").value("Ali"))
                .andExpect(jsonPath("$[1].lastName").value("Veli"))
                .andExpect(jsonPath("$[1].company").value("Arçelik"));
    }

}
