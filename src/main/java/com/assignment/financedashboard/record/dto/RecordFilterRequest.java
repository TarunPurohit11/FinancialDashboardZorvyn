package com.assignment.financedashboard.record.dto;

import com.assignment.financedashboard.common.enums.RecordType;

import java.time.LocalDate;

public class RecordFilterRequest {

    public RecordType type;
    public String category;
    public LocalDate startDate;
    public LocalDate endDate;
}
