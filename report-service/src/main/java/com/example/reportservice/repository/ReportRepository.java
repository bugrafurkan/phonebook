package com.example.reportservice.repository;

import com.example.reportservice.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository <Report, String>{
}
