package com.assignment.financedashboard.record.controller;

import com.assignment.financedashboard.common.response.ApiResponse;
import com.assignment.financedashboard.record.dto.*;
import com.assignment.financedashboard.record.service.FinancialRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class FinancialRecordController {

    private final FinancialRecordService service;

    @PostMapping
    public ResponseEntity<ApiResponse<RecordResponse>> create(@Valid @RequestBody CreateRecordRequest req) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Created record successfully", service.create(req)));
    }

    @GetMapping
    public Page<?> getAll(
            RecordFilterRequest filter,
            Pageable pageable
    ) {
        return service.getAll(filter, pageable);
    }

    @GetMapping("/{id}")
    public RecordDetailedResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public RecordResponse update(@PathVariable Long id,
                                 @Valid @RequestBody UpdateRecordRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/insights")
    public AnalystInsightResponse insights() {
        return service.getInsights();
    }
}

