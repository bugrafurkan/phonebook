package com.example.reportservice.service;

import com.example.reportservice.dto.ReportDto;

import java.util.List;


public interface ReportService {
    List<ReportDto> getAllReports();

    ReportDto requestReport();

    ReportDto getReportById(Long id);

    void prepareReportAsync(Long id);
}
