package com.example.budgetingapp.expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Long> {
}
