package com.example.reportservice.controller;

import com.example.reportservice.dto.ReportDto;
import com.example.reportservice.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
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

    @PostMapping("/create")
    public ResponseEntity<ReportDto> createReport() {
        ReportDto created = reportService.requestReport();
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ReportDto>> getReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/getReportById")
    public ResponseEntity<ReportDto> getReportById(@PathVariable Long id) {
        ReportDto reportDto = reportService.getReportById(id);
        if (Objects.isNull(reportDto)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportDto);
    }

    @PostMapping("/prepare")
    public ResponseEntity<Void> prepareReport(@PathVariable Long id) {
        reportService.prepareReportAsync(id);
        return ResponseEntity.ok().build();
    }
}
