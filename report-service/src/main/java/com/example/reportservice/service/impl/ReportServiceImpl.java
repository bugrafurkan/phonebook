package com.example.reportservice.service.impl;

import com.example.reportservice.client.DirectoryFeignClient;
import com.example.reportservice.client.PersonResponse;
import com.example.reportservice.dto.ReportDto;
import com.example.reportservice.entity.Report;
import com.example.reportservice.entity.ReportStatus;
import com.example.reportservice.repository.ReportRepository;
import com.example.reportservice.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Service
public class ReportServiceImpl implements ReportService {

    private static final String BASE_URL = "http://localhost:8080/api/reports";
    private final ReportRepository reportRepository;
    private final DirectoryFeignClient directoryFeignClient;

    public ReportServiceImpl(ReportRepository reportRepository, DirectoryFeignClient directoryFeignClient) {
        this.reportRepository = reportRepository;
        this.directoryFeignClient = directoryFeignClient;
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
    public ReportDto getReportById(Long id) {
        return reportRepository.findById(id)
                .map(this::toReportDto)
                .orElse(null);
    }

    @Override
    public void prepareReportAsync(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) return;

        List<PersonResponse> persons = directoryFeignClient.getPersonsWithContacts();

        Map<String, LocationStats> locationMap = new HashMap<>();

        for (PersonResponse p : persons) {
            // Kişinin Contact listesinde LOCATION tipindekileri ara
            p.getContacts().stream()
                    .filter(c -> !c.getLocation().isBlank())
                    .forEach(contact -> {
                        String location = contact.getLocation(); // Örn. "Istanbul"

                        locationMap.putIfAbsent(location, new LocationStats(location));
                        LocationStats stats = locationMap.get(location);

                        // Her kişi için istatistikleri artır
                        if (stats != null) {
                            stats.increasePersonCount(1); // Kişi sayısını 1 artır

                            // Eğer iletişim tipi telefon ise telefon sayısını artır
                            if ("PHONE".equals(contact.getContactType())) {
                                stats.increasePhoneCount(1);
                            }
                        }
                    });
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String reportDataJson = objectMapper.writeValueAsString(locationMap.values());
            report.setReport(reportDataJson);
            report.setStatus(ReportStatus.COMPLETED);
            reportRepository.save(report);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public static class LocationStats {
        private String location;
        private int personCount;
        private int phoneCount;


        public LocationStats(String locationName) {
            this.location = locationName;
            this.personCount = 0;
            this.phoneCount = 0;
        }

        public void increasePersonCount(int count) {
            this.personCount += count;
        }

        public void increasePhoneCount(int count) {
            this.phoneCount += count;
        }

        // Getter ve Setter'lar
        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getPersonCount() {
            return personCount;
        }

        public void setPersonCount(int personCount) {
            this.personCount = personCount;
        }

        public int getPhoneCount() {
            return phoneCount;
        }

        public void setPhoneCount(int phoneCount) {
            this.phoneCount = phoneCount;
        }

        // toString metodu (isteğe bağlı)
        @Override
        public String toString() {
            return "LocationStats{" +
                    "location='" + location + '\'' +
                    ", personCount=" + personCount +
                    ", phoneCount=" + phoneCount +
                    '}';
        }
    }

}

