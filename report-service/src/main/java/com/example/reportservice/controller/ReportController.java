package com.example.reportservice.controller;

import com.example.reportservice.dto.ReportDto;
import com.example.reportservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Report Management", description = "APIs for managing reports")
@RequestMapping("api/reports")
public class ReportController {

    private static final String BASE_URL = "http://localhost:8080/api/reports";
    private static final String GET_REPORTS = BASE_URL + "/getReports";
    private static final String GET_REPORT_BY_ID = BASE_URL + "/getReportById";
    private static final String GET_REPORT_BY_NAME = BASE_URL + "/getReportByName";
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

@io.swagger.v3.oas.annotations.Operation(summary = "Create a new report", description = "Generates a report and returns the created report data.")
@PostMapping("/create")
    public ResponseEntity<ReportDto> createReport() {
        ReportDto created = reportService.requestReport();
        return ResponseEntity.ok(created);
    }

@io.swagger.v3.oas.annotations.Operation(summary = "Get all reports", description = "Fetches all reports that are available.")
@GetMapping
    public ResponseEntity<List<ReportDto>> getReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

@io.swagger.v3.oas.annotations.Operation(summary = "Get report by ID", description = "Fetch a specific report by its unique ID.")
@Parameter(name = "id", description = "Unique identifier of the report to fetch", required = true)
@GetMapping("/getReportById")
public ResponseEntity<ReportDto> getReportById(@PathVariable Long id) {
        ReportDto reportDto = reportService.getReportById(id);
        if (Objects.isNull(reportDto)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDto);
    }

@io.swagger.v3.oas.annotations.Operation(summary = "Prepare a report", description = "Initiates an asynchronous preparation for the specified report.")
@Parameter(name = "id", description = "Unique identifier of the report to prepare")
@PostMapping("/prepare")
public ResponseEntity<Void> prepareReport(@PathVariable Long id) {
        reportService.prepareReportAsync(id);
        return ResponseEntity.ok().build();
    }
}
