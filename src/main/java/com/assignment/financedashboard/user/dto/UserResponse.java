package com.assignment.financedashboard.user.dto;

import com.assignment.financedashboard.common.enums.Role;
import com.assignment.financedashboard.common.enums.Status;

public class UserResponse {

    public Long id;
    public String name;
    public String email;
    public Role role;
    public Status status;
}
