package com.assignment.financedashboard.user.service;

import com.assignment.financedashboard.user.dto.CreateUserRequest;
import com.assignment.financedashboard.user.dto.UpdateRoleRequest;
import com.assignment.financedashboard.user.dto.UpdateStatusRequest;
import com.assignment.financedashboard.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse create(CreateUserRequest request);

    List<UserResponse> getAll();

    UserResponse updateRole(Long id, UpdateRoleRequest request);

    UserResponse updateStatus(Long id, UpdateStatusRequest request);
}
