package com.reunitex;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissingReportRepository
        extends JpaRepository<MissingReport, Long> {

    List<MissingReport> findByFamilyId(String familyId);

    List<MissingReport> findByPersonNameContainingIgnoreCase(String personName);
}