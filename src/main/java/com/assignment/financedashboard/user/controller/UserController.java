package com.assignment.financedashboard.user.controller;

import com.assignment.financedashboard.common.response.ApiResponse;
import com.assignment.financedashboard.user.dto.CreateUserRequest;
import com.assignment.financedashboard.user.dto.UpdateRoleRequest;
import com.assignment.financedashboard.user.dto.UpdateStatusRequest;
import com.assignment.financedashboard.user.dto.UserResponse;
import com.assignment.financedashboard.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Valid @RequestBody CreateUserRequest req) {
        return ResponseEntity.ok(new ApiResponse<>(true, "User created successfully", service.create(req)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Users fetched successfully", service.getAll()));
    }

    @PatchMapping("/{id}/role")
    public UserResponse updateRole(@PathVariable Long id,
                                   @Valid @RequestBody UpdateRoleRequest req) {
        return service.updateRole(id, req);
    }

    @PatchMapping("/{id}/status")
    public UserResponse updateStatus(@PathVariable Long id,
                                     @Valid @RequestBody UpdateStatusRequest req) {
        return service.updateStatus(id, req);
    }
}
