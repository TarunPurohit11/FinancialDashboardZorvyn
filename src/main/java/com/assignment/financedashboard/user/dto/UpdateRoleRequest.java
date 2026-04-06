package com.assignment.financedashboard.user.dto;

import com.assignment.financedashboard.common.enums.Role;
import jakarta.validation.constraints.NotNull;

public class UpdateRoleRequest {

    @NotNull
    public Role role;
}
