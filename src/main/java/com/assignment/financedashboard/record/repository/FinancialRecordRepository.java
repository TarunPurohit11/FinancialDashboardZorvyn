package com.assignment.financedashboard.record.repository;

import com.assignment.financedashboard.common.enums.RecordType;
import com.assignment.financedashboard.record.entity.FinancialRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    @Query("SELECT SUM(fr.amount) FROM FinancialRecord fr WHERE fr.type='INCOME' AND fr.deletedAt IS NULL")
    BigDecimal totalIncome();

    @Query("SELECT SUM(fr.amount) FROM FinancialRecord fr WHERE fr.type='EXPENSE' AND fr.deletedAt IS NULL")
    BigDecimal totalExpense();

    Page<FinancialRecord> findByDeletedAtIsNull(Pageable pageable);

    @Query("""
        SELECT fr FROM FinancialRecord fr
        WHERE fr.deletedAt IS NULL
        AND (:type IS NULL OR fr.type = :type)
        AND (:category IS NULL OR fr.category = :category)
        AND (:startDate IS NULL OR fr.recordDate >= :startDate)
        AND (:endDate IS NULL OR fr.recordDate <= :endDate)
    """)
    Page<FinancialRecord> filter(
            @Param("type") RecordType type,
            @Param("category") String category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    @Query("""
        SELECT fr.category, SUM(fr.amount)
        FROM FinancialRecord fr
        WHERE fr.deletedAt IS NULL
        GROUP BY fr.category
    """)
    List<Object[]> categoryTotals();

    @Query("""
        SELECT FUNCTION('DATE_FORMAT', fr.recordDate, '%Y-%m'),
               SUM(CASE WHEN fr.type = 'INCOME' THEN fr.amount ELSE 0 END),
               SUM(CASE WHEN fr.type = 'EXPENSE' THEN fr.amount ELSE 0 END)
        FROM FinancialRecord fr
        WHERE fr.deletedAt IS NULL
        GROUP BY FUNCTION('DATE_FORMAT', fr.recordDate, '%Y-%m')
        ORDER BY 1
    """)
    List<Object[]> monthlyTrends();

    @Query("""
        SELECT fr FROM FinancialRecord fr
        WHERE fr.deletedAt IS NULL
        ORDER BY fr.recordDate DESC
    """)
    List<FinancialRecord> recent(org.springframework.data.domain.Pageable pageable);
}