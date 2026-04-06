package com.assignment.financedashboard.user.service;


import com.assignment.financedashboard.common.enums.Status;
import com.assignment.financedashboard.user.dto.CreateUserRequest;
import com.assignment.financedashboard.user.dto.UpdateRoleRequest;
import com.assignment.financedashboard.user.dto.UpdateStatusRequest;
import com.assignment.financedashboard.user.dto.UserResponse;
import com.assignment.financedashboard.user.entity.User;
import com.assignment.financedashboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    private final PasswordEncoder encoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse create(CreateUserRequest req) {

        if (repo.findByEmail(req.email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPasswordHash(encoder.encode(req.password));
        user.setRole(req.role);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        repo.save(user);

        return map(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAll() {
        return repo.findAll().stream().map(this::map).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateRole(Long id, UpdateRoleRequest req) {
        User user = repo.findById(id).orElseThrow();
        user.setRole(req.role);
        repo.save(user);
        return map(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateStatus(Long id, UpdateStatusRequest req) {
        User user = repo.findById(id).orElseThrow();
        user.setStatus(req.status);
        repo.save(user);
        return map(user);
    }

    private UserResponse map(User user) {
        UserResponse res = new UserResponse();
        res.id = user.getId();
        res.name = user.getName();
        res.email = user.getEmail();
        res.role = user.getRole();
        res.status = user.getStatus();
        return res;
    }
}
