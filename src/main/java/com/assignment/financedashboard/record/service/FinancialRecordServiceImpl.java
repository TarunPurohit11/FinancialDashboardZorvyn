package com.assignment.financedashboard.record.service;


import com.assignment.financedashboard.common.util.SecurityUtil;
import com.assignment.financedashboard.record.dto.*;
import com.assignment.financedashboard.record.entity.FinancialRecord;
import com.assignment.financedashboard.record.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository repo;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse create(CreateRecordRequest req) {
        FinancialRecord fr = new FinancialRecord();

        fr.setAmount(req.amount);
        fr.setType(req.type);
        fr.setCategory(req.category);
        fr.setRecordDate(req.recordDate);
        fr.setNotes(req.notes);

        repo.save(fr);
        return map(fr);
    }

    @Override
    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public Page<?> getAll(RecordFilterRequest filter, Pageable pageable) {

        String role = SecurityUtil.getCurrentRole();

        return repo.filter(
                filter.type,
                filter.category,
                filter.startDate,
                filter.endDate,
                pageable
        ).map(fr -> {

            if (role.equals("ROLE_VIEWER")) {
                RecordViewerResponse r = new RecordViewerResponse();
                r.id = fr.getId();
                r.amount = fr.getAmount();
                r.type = fr.getType();
                r.category = fr.getCategory();
                r.recordDate = fr.getRecordDate();
                return r;
            }

            // Analyst/Admin
            RecordDetailedResponse r = new RecordDetailedResponse();
            r.id = fr.getId();
            r.amount = fr.getAmount();
            r.type = fr.getType();
            r.category = fr.getCategory();
            r.recordDate = fr.getRecordDate();
            r.notes = fr.getNotes();
            r.createdBy = fr.getCreatedBy() != null ? fr.getCreatedBy().getEmail() : null;

            return r;
        });
    }

    @Override
    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public RecordDetailedResponse getById(Long id) {

        FinancialRecord fr = repo.findById(id).orElseThrow();

        RecordDetailedResponse res = new RecordDetailedResponse();
        res.id = fr.getId();
        res.amount = fr.getAmount();
        res.type = fr.getType();
        res.category = fr.getCategory();
        res.recordDate = fr.getRecordDate();
        res.notes = fr.getNotes();
        res.createdBy = fr.getCreatedBy() != null ? fr.getCreatedBy().getEmail() : null;

        return res;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse update(Long id, UpdateRecordRequest req) {

        FinancialRecord fr = repo.findById(id).orElseThrow();

        fr.setAmount(req.amount);
        fr.setType(req.type);
        fr.setCategory(req.category);
        fr.setRecordDate(req.recordDate);
        fr.setNotes(req.notes);

        repo.save(fr);
        return map(fr);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        FinancialRecord fr = repo.findById(id).orElseThrow();
        fr.setDeletedAt(LocalDateTime.now());
        repo.save(fr);
    }

    private RecordResponse map(FinancialRecord fr) {
        RecordResponse r = new RecordResponse();
        r.id = fr.getId();
        r.amount = fr.getAmount();
        r.type = fr.getType();
        r.category = fr.getCategory();
        r.recordDate = fr.getRecordDate();
        return r;
    }

    @PreAuthorize("hasAnyRole('ANALYST','ADMIN')")
    public AnalystInsightResponse getInsights() {

        BigDecimal income = repo.totalIncome();
        BigDecimal expense = repo.totalExpense();

        AnalystInsightResponse res = new AnalystInsightResponse();

        if (income != null && income.compareTo(BigDecimal.ZERO) > 0) {
            res.expenseRatio = expense.divide(income, 2, java.math.RoundingMode.HALF_UP);
        } else {
            res.expenseRatio = BigDecimal.ZERO;
        }

        // Top category
        var list = repo.categoryTotals();

        if (!list.isEmpty()) {
            Object[] top = list.stream()
                    .max((a, b) -> ((BigDecimal) a[1]).compareTo((BigDecimal) b[1]))
                    .orElse(null);

            if (top != null) {
                res.topCategory = (String) top[0];
                res.topCategorySpend = (BigDecimal) top[1];
            }
        }

        return res;
    }
}
