package com.example.reportservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReportDto {
    private String id;
    private LocalDateTime requestTimeAt;
    private String status;
    private String reportData;
}
