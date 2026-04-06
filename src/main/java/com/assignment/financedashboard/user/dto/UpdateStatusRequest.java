package com.assignment.financedashboard.user.dto;

import com.assignment.financedashboard.common.enums.Status;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {

    @NotNull
    public Status status;
}
