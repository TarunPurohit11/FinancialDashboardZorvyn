package com.assignment.financedashboard.dashboard.service;

import com.assignment.financedashboard.dashboard.dto.*;
import com.assignment.financedashboard.record.entity.FinancialRecord;
import com.assignment.financedashboard.record.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    
    private final FinancialRecordRepository repo;

    @Override
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public DashboardFullResponse getDashboard() {

        DashboardFullResponse res = new DashboardFullResponse();

        // Summary
        BigDecimal income = repo.totalIncome();
        BigDecimal expense = repo.totalExpense();

        DashboardSummaryResponse summary = new DashboardSummaryResponse();
        summary.totalIncome = income != null ? income : BigDecimal.ZERO;
        summary.totalExpense = expense != null ? expense : BigDecimal.ZERO;
        summary.netBalance = summary.totalIncome.subtract(summary.totalExpense);

        res.summary = summary;

        // Category totals
        res.categoryTotals = repo.categoryTotals().stream().map(obj -> {
            CategoryTotalResponse r = new CategoryTotalResponse();
            r.category = (String) obj[0];
            r.total = (BigDecimal) obj[1];
            return r;
        }).toList();

        // Monthly trends
        res.monthlyTrends = repo.monthlyTrends().stream().map(obj -> {
            MonthlyTrendResponse r = new MonthlyTrendResponse();
            r.month = (String) obj[0];
            r.income = (BigDecimal) obj[1];
            r.expense = (BigDecimal) obj[2];
            return r;
        }).toList();

        // Recent activity
        List<FinancialRecord> recent = repo.recent(PageRequest.of(0, 5));

        res.recentActivities = recent.stream().map(fr -> {
            RecentActivityResponse r = new RecentActivityResponse();
            r.id = fr.getId();
            r.amount = fr.getAmount();
            r.type = fr.getType();
            r.category = fr.getCategory();
            r.date = fr.getRecordDate();
            return r;
        }).toList();

        return res;
    }
}

