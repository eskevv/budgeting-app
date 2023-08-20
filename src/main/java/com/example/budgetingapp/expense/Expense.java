package com.example.budgetingapp.expense;

import com.example.budgetingapp.budget.Budget;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class Expense {
  @Id
  @SequenceGenerator(name = "expense_sequence", sequenceName = "expense_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expense_sequence")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "budget_id")
  private Budget budget;
  private Long createdAt;
  private String expenseCategory;
  private String expenseName;
  private BigDecimal amount;

  public Expense() {
  }

  public Expense(Long id, Budget budget, Long createdAt, String expenseCategory, String expenseName, BigDecimal amount) {
    this.id = id;
    this.budget = budget;
    this.createdAt = createdAt;
    this.expenseCategory = expenseCategory;
    this.expenseName = expenseName;
    this.amount = amount;
  }

  public Expense(Budget budget, Long createdAt, String expenseCategory, String expenseName, BigDecimal amount) {
    this.budget = budget;
    this.createdAt = createdAt;
    this.expenseCategory = expenseCategory;
    this.expenseName = expenseName;
    this.amount = amount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Budget getBudget() {
    return budget;
  }

  public void setBudget(Budget budget) {
    this.budget = budget;
  }

  public String getExpenseCategory() {
    return expenseCategory;
  }

  public void setExpenseCategory(String expenseCategory) {
    this.expenseCategory = expenseCategory;
  }

  public String getExpenseName() {
    return expenseName;
  }

  public void setExpenseName(String expenseName) {
    this.expenseName = expenseName;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }
}
