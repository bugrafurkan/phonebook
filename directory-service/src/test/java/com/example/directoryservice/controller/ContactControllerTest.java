package com.example.directoryservice.controller;

import com.example.directoryservice.DirectoryServiceApplication;
import com.example.directoryservice.dto.ContactDto;
import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.repository.PersonRepository;
import com.example.directoryservice.service.ContactService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
@ContextConfiguration(classes = DirectoryServiceApplication.class)
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactService contactService;

    @MockBean
    private PersonRepository personRepository;

    @Test
    void addContact_ShouldReturnCreated_WhenValidRequest() throws Exception {
        // Arrange
        String personId = "123";
        ContactDto contactDto = new ContactDto();
        contactDto.setPersonId(personId);

        Long contactId = 456L;
        Contact contact = new Contact();
        contact.setId(contactId);

        Mockito.when(personRepository.existsById(personId)).thenReturn(true);
        Mockito.when(contactService.createContact(any(ContactDto.class))).thenReturn(contact);

        // Act & Assert
        mockMvc.perform(post("/api/contacts") // "/contacts" uygun URL dizinine göre değiştirin
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"personId\": \"123\", \"firstName\": \"John\", \"lastName\": \"Doe\" }")) // JSON payload örneği
                .andExpect(status().isCreated());
    }

    @Test
    void addContact_ShouldReturnBadRequest_WhenPersonIdIsInvalid() throws Exception {
        // Arrange
        String invalidPersonId = "";

        ContactDto contactDto = new ContactDto();
        contactDto.setPersonId(invalidPersonId);

        Mockito.when(personRepository.existsById(invalidPersonId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"personId\": \"\", \"firstName\": \"John\", \"lastName\": \"Doe\" }")) // JSON payload
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteContact_ShouldReturnNoContent_WhenContactExists() throws Exception {
        // Arrange
        Long contactId = 123L;

        Mockito.doNothing().when(contactService).deleteContact(eq(contactId));

        // Act & Assert
        mockMvc.perform(delete("/api/contacts/{id}", contactId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteContact_ShouldReturnBadRequest_WhenContactDoesNotExist() throws Exception {
        // Arrange
        Long invalidContactId = 999L;

        Mockito.doThrow(new IllegalArgumentException("Contact not found"))
                .when(contactService).deleteContact(eq(invalidContactId));

        // Act & Assert
        mockMvc.perform(delete("/api/contacts/{id}", invalidContactId))
                .andExpect(status().isBadRequest());
    }
}
