package com.assignment.financedashboard.record.dto;

import com.assignment.financedashboard.common.enums.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateRecordRequest {

    @NotNull
    @Positive(message = "Amount must be greater than 0")
    public BigDecimal amount;

    @NotNull
    public RecordType type;

    @NotBlank(message = "Category is required")
    public String category;

    @NotNull
    public LocalDate recordDate;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    public String notes;
}
