package com.assignment.financedashboard.dashboard.dto;

import java.util.List;

public class DashboardFullResponse {

    public DashboardSummaryResponse summary;
    public List<CategoryTotalResponse> categoryTotals;
    public List<MonthlyTrendResponse> monthlyTrends;
    public List<RecentActivityResponse> recentActivities;
}
