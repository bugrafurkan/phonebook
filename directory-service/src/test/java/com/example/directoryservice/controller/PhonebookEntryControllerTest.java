package com.example.directoryservice.controller;

import com.example.directoryservice.DirectoryServiceApplication;
import com.example.directoryservice.dto.PersonDto;
import com.example.directoryservice.dto.PhonebookEntryDto;
import com.example.directoryservice.entity.PhonebookEntry;
import com.example.directoryservice.service.PhonebookEntryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(PhonebookEntryController.class)
@ContextConfiguration(classes = DirectoryServiceApplication.class)
class PhonebookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhonebookEntryService phonebookEntryService;

    // Test for getEntryById
    @Test
    void getEntryById_ShouldReturnEntry_WhenEntryExists() throws Exception {
        PersonDto person = new PersonDto();
        person.setFirstName("John");
        person.setLastName("Doe");

        PhonebookEntryDto entry = new PhonebookEntryDto();
        entry.setId(1L);
        entry.setOwnerId("owner1");
        entry.setContactId("contact1");
        entry.setContactPerson(person);

        Mockito.when(phonebookEntryService.getPhonebookEntryById(1L)).thenReturn(entry);

        mockMvc.perform(get("/api/phonebook-entries/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ownerId").value("owner1"));
    }

    @Test
    void getEntryById_ShouldReturnNotFound_WhenEntryDoesNotExist() throws Exception {
        Mockito.when(phonebookEntryService.getPhonebookEntryById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/phonebook-entries/1"))
                .andExpect(status().isNotFound());
    }

    // Test for getAllEntries
    @Test
    void getAllEntries_ShouldReturnAllEntries() throws Exception {
        PersonDto person1 = new PersonDto();
        person1.setFirstName("John");
        person1.setLastName("Doe");

        PersonDto person2 = new PersonDto();
        person2.setFirstName("Jane");
        person2.setLastName("Smith");

        PhonebookEntryDto entry1 = new PhonebookEntryDto();
        entry1.setId(1L);
        entry1.setOwnerId("owner1");
        entry1.setContactId("contact1");
        entry1.setContactPerson(person1);

        PhonebookEntryDto entry2 = new PhonebookEntryDto();
        entry2.setId(2L);
        entry2.setOwnerId("owner2");
        entry2.setContactId("contact2");
        entry2.setContactPerson(person2);

        List<PhonebookEntryDto> entries = Arrays.asList(entry1, entry2);

        Mockito.when(phonebookEntryService.getAllPhonebookEntries()).thenReturn(entries);

        mockMvc.perform(get("/api/phonebook-entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].ownerId").value("owner1"))
                .andExpect(jsonPath("$[1].ownerId").value("owner2"))
                .andExpect(jsonPath("$[0].contactPerson.firstName").value("John"))
                .andExpect(jsonPath("$[1].contactPerson.lastName").value("Smith"));
    }

    @Test
    void getAllEntries_ShouldReturnEmptyList_WhenNoEntriesExist() throws Exception {
        Mockito.when(phonebookEntryService.getAllPhonebookEntries()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/phonebook-entries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    // Test for addEntry
    @Test
    void addEntry_ShouldReturnCreated_WhenEntryIsValid() throws Exception {
        PersonDto person = new PersonDto();
        person.setFirstName("John");
        person.setLastName("Doe");

        PhonebookEntryDto createdEntry = new PhonebookEntryDto();
        createdEntry.setId(1L);
        createdEntry.setOwnerId("owner1");
        createdEntry.setContactId("contact1");
        createdEntry.setContactPerson(person);

        Mockito.when(phonebookEntryService.addPhonebookEntry(any(PhonebookEntryDto.class))).thenReturn(createdEntry);

        mockMvc.perform(post("/api/phonebook-entries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerId\":\"owner1\",\"contactId\":\"contact1\",\"contactPerson\":{\"firstName\":\"John\",\"lastName\":\"Doe\"}}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/phonebook-entries/1"));
    }

    // Test for updateEntry
    @Test
    void updateEntry_ShouldReturnUpdatedEntry_WhenEntryExists() throws Exception {
        PersonDto person = new PersonDto();
        person.setFirstName("John");
        person.setLastName("Doe Updated");

        PhonebookEntryDto updatedEntry = new PhonebookEntryDto();
        updatedEntry.setId(1L);
        updatedEntry.setOwnerId("owner1");
        updatedEntry.setContactId("contact1");
        updatedEntry.setContactPerson(person);

        Mockito.when(phonebookEntryService.updatePhonebookEntry(eq(1L), any(PhonebookEntryDto.class))).thenReturn(updatedEntry);

        mockMvc.perform(put("/api/phonebook-entries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerId\":\"owner1\",\"contactId\":\"contact1\",\"contactPerson\":{\"firstName\":\"John\",\"lastName\":\"Doe Updated\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactPerson.lastName").value("Doe Updated"));
    }

    @Test
    void updateEntry_ShouldReturnNotFound_WhenEntryDoesNotExist() throws Exception {
        Mockito.when(phonebookEntryService.updatePhonebookEntry(eq(1L), any(PhonebookEntryDto.class))).thenReturn(null);

        mockMvc.perform(put("/api/phonebook-entries/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"ownerId\":\"owner1\",\"contactId\":\"contact1\",\"contactPerson\":{\"firstName\":\"John\",\"lastName\":\"Doe\"}}"))
                .andExpect(status().isNotFound());
    }

    // Test for deleteEntry
    @Test
    void deleteEntry_ShouldReturnNoContent_WhenEntryExists() throws Exception {
        Mockito.doNothing().when(phonebookEntryService).deletePhonebookEntry(1L);

        mockMvc.perform(delete("/api/phonebook-entries/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteEntry_ShouldHandleError_WhenEntryDoesNotExist() throws Exception {
        Mockito.doThrow(new IllegalArgumentException("Entry not found"))
                .when(phonebookEntryService).deletePhonebookEntry(1L);

        mockMvc.perform(delete("/api/phonebook-entries/1"))
                .andExpect(status().isBadRequest());
    }

    // Test for getEntriesOfOwner
    @Test
    void getEntriesOfOwner_ShouldReturnOwnersEntries() throws Exception {
        PersonDto person = new PersonDto();
        person.setFirstName("John");
        person.setLastName("Doe");

        PhonebookEntryDto entry = new PhonebookEntryDto();
        entry.setId(1L);
        entry.setOwnerId("owner1");
        entry.setContactId("contact1");
        entry.setContactPerson(person);

        List<PhonebookEntryDto> entries = List.of(entry);

        Mockito.when(phonebookEntryService.findEntriesOfOwner("owner1")).thenReturn(entries);

        mockMvc.perform(get("/api/phonebook-entries/owner/owner1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].ownerId").value("owner1"))
                .andExpect(jsonPath("$[0].contactPerson.firstName").value("John"));
    }

    @Test
    void getEntriesOfOwner_ShouldReturnEmptyList_WhenOwnerHasNoEntries() throws Exception {
        Mockito.when(phonebookEntryService.findEntriesOfOwner("owner1")).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/phonebook-entries/owner/owner1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

}