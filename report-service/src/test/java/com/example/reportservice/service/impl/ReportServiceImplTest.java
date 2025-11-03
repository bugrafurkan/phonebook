package com.example.reportservice.service.impl;

import com.example.directoryservice.entity.Contact;
import com.example.directoryservice.entity.ContactType;
import com.example.reportservice.client.DirectoryFeignClient;
import com.example.reportservice.client.PersonResponse;
import com.example.reportservice.entity.Report;
import com.example.reportservice.entity.ReportStatus;
import com.example.reportservice.repository.ReportRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private DirectoryFeignClient directoryFeignClient;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void prepareReportAsync_shouldIncreasePhoneCountWhenPhoneContactsPresent() throws Exception {
        Long reportId = 1L;
        Report report = new Report();
        report.setId(reportId);
        report.setStatus(ReportStatus.PREPARING);

        Contact phoneContact = new Contact();
        phoneContact.setContactType(ContactType.PHONE);
        phoneContact.setLocation("Istanbul");

        PersonResponse person = new PersonResponse();
        person.setContacts(List.of(phoneContact));

        when(reportRepository.findById(String.valueOf(reportId))).thenReturn(Optional.of(report));
        when(directoryFeignClient.getPersonsWithContacts()).thenReturn(List.of(person));

        reportService.prepareReportAsync(reportId);

        verify(directoryFeignClient).getPersonsWithContacts();
        verify(reportRepository).save(report);

        assertEquals(ReportStatus.COMPLETED, report.getStatus());
        assertNotNull(report.getReport());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode reportJson = objectMapper.readTree(report.getReport());
        assertTrue(reportJson.isArray());
        assertEquals(1, reportJson.size());

        JsonNode locationStats = reportJson.get(0);
        assertEquals("Istanbul", locationStats.get("location").asText());
        assertEquals(1, locationStats.get("personCount").asInt());
        assertEquals(1, locationStats.get("phoneCount").asInt());
    }
}
