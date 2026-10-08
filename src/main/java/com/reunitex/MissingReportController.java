package com.reunitex;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin
public class MissingReportController {

    private final MissingReportRepository reportRepository;

    public MissingReportController(
            MissingReportRepository reportRepository) {

        this.reportRepository = reportRepository;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitReport(
            @RequestBody MissingReport report) {

        String caseId;

        do {
            caseId =
                    "CASE-" +
                    (10000 + new Random().nextInt(90000));

        } while (isCaseIdExists(caseId));

        report.setCaseId(caseId);

        MissingReport saved =
                reportRepository.save(report);

        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message",
                        "Missing Person Report Submitted Successfully",
                        "caseId",
                        saved.getCaseId()
                )
        );
    }

    private boolean isCaseIdExists(String caseId) {

        return reportRepository
                .findAll()
                .stream()
                .anyMatch(report ->
                        caseId.equals(report.getCaseId()));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchReports(
            @RequestParam String query) {

        query = query.trim();

        List<MissingReport> reports =
                reportRepository
                        .findByPersonNameContainingIgnoreCase(query);

        if (reports.isEmpty()) {

            reports =
                    reportRepository
                            .findByFamilyId(query);
        }

        if (reports.isEmpty()) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(reports);
    }
}