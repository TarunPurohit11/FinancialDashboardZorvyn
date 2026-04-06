package com.assignment.financedashboard.dashboard.dto;

import com.assignment.financedashboard.common.enums.RecordType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecentActivityResponse {
    public Long id;
    public BigDecimal amount;
    public RecordType type;
    public String category;
    public LocalDate date;
}