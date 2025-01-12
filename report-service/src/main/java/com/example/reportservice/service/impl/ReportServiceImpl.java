package com.example.reportservice.service.impl;

import com.example.reportservice.dto.ReportDto;
import com.example.reportservice.entity.Report;
import com.example.reportservice.entity.ReportStatus;
import com.example.reportservice.repository.ReportRepository;
import com.example.reportservice.service.ReportService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class ReportServiceImpl implements ReportService {

    private static final String BASE_URL = "http://localhost:8080/api/reports";
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(this::toReportDto)
                .toList();
    }

    private ReportDto toReportDto(Report report) {
        if (report == null) return null;

        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setRequestTimeAt(report.getRequestTimeAt());
        dto.setStatus(report.getStatus().name());
        dto.setReportData(report.getReport());
        return dto;
    }

    @Override
    public ReportDto requestReport() {

        Report report = new Report();
        report.setRequestTimeAt(LocalDateTime.now());
        report.setStatus(ReportStatus.PREPARING);
        report.setReport(null);
        Report saved = reportRepository.save(report);
        return null;
    }

    @Override
    public ReportDto getReportById(String id) {
        return reportRepository.findById(id)
                .map(this::toReportDto)
                .orElse(null);
    }

    @Override
    public void prepareReportAsync(String id) {
        Report report = reportRepository.findById(id).orElse(null);
        if(Objects.isNull(report)) return;
        report.setReport("statsJson");
        report.setStatus(ReportStatus.COMPLETED);
        reportRepository.save(report);
    }
}
