package com.assignment.financedashboard.dashboard.controller;

import com.assignment.financedashboard.dashboard.dto.DashboardFullResponse;
import com.assignment.financedashboard.dashboard.dto.DashboardSummaryResponse;
import com.assignment.financedashboard.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    public DashboardFullResponse getDashboard() {
        return service.getDashboard();
    }
}
