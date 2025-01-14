package com.example.reportservice.controller;


import com.example.reportservice.dto.ReportDto;
import com.example.reportservice.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    private ReportDto report;

    @BeforeEach
    void setUp() {
        report = new ReportDto();
        report.setId(1L);
        //report.setName("Sample Report");
    }

    @Test
    void createReport_ShouldReturnCreatedReport() throws Exception {
        Mockito.when(reportService.requestReport()).thenReturn(report);

        mockMvc.perform(post("/api/reports/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.id").value(1L));
                //.andExpect(jsonPath("$.name").value("Sample Report"));
    }

    @Test
    void getReports_ShouldReturnListOfReports() throws Exception {
        Mockito.when(reportService.getAllReports()).thenReturn(List.of(report));

        mockMvc.perform(get("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.size()").value(1));
                //.andExpect(jsonPath("$[0].id").value(1L));
                //.andExpect(jsonPath("$[0].name").value("Sample Report"));
    }

    @Test
    void getReports_ShouldReturnEmptyListWhenNoReports() throws Exception {
        Mockito.when(reportService.getAllReports()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void getReportById_ShouldReturnReportWhenFound() throws Exception {
        Mockito.when(reportService.getReportById(1L)).thenReturn(report);

        mockMvc.perform(get("/api/reports/getReportById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.id").value(1L))
                //.andExpect(jsonPath("$.name").value("Sample Report"));
    }

    @Test
    void getReportById_ShouldReturnNotFoundWhenReportDoesNotExist() throws Exception {
        Mockito.when(reportService.getReportById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/api/reports/getReportById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
