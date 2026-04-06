package com.assignment.financedashboard.record.service;

import com.assignment.financedashboard.record.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FinancialRecordService {

    RecordResponse create(CreateRecordRequest request);

    Page<?> getAll(RecordFilterRequest filter, Pageable pageable);

    RecordDetailedResponse getById(Long id);

    RecordResponse update(Long id, UpdateRecordRequest request);

    void delete(Long id);
    public AnalystInsightResponse getInsights();
}
