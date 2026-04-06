package com.assignment.financedashboard.record.dto;

import com.assignment.financedashboard.common.enums.RecordType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateRecordRequest {

    @NotNull
    @DecimalMin("0.01")
    public BigDecimal amount;

    @NotNull
    public RecordType type;

    @NotBlank
    public String category;

    @NotNull
    public LocalDate recordDate;

    public String notes;
}
