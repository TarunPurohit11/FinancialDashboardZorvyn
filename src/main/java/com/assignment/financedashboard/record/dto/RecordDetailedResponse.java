package com.assignment.financedashboard.record.dto;

import com.assignment.financedashboard.common.enums.RecordType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecordDetailedResponse {

    public Long id;
    public BigDecimal amount;
    public RecordType type;
    public String category;
    public LocalDate recordDate;
    public String notes;
    public String createdBy;
}
