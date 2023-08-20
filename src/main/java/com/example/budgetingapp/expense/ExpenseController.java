package com.example.budgetingapp.expense;

import com.example.budgetingapp.budget.IBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/expenses")
public class ExpenseController {

  private final IExpenseRepository expenseRepository;
  private final IBudgetRepository budgetRepository;

  @Autowired
  public ExpenseController(IExpenseRepository expenseRepository, IBudgetRepository budgetRepository) {
    this.expenseRepository = expenseRepository;
    this.budgetRepository = budgetRepository;
  }

  @GetMapping
  public ResponseEntity<List<Expense>> getAllExpenses() {
    List<Expense> expenses = expenseRepository.findAll();
    return ResponseEntity.ok(expenses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
    Optional<Expense> expense = expenseRepository.findById(id);
    return expense.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Expense> createExpense(@RequestBody ExpenseDTO expenseDTO) {
    var foundBudget = budgetRepository.findById(expenseDTO.budgetId).orElseThrow();
    var newExpense = new Expense(foundBudget, 901L, expenseDTO.expenseCategory, expenseDTO.expenseName, expenseDTO.amount);
    Expense expense = expenseRepository.save(newExpense);
    return ResponseEntity.created(URI.create("/api/expenses/" + newExpense.getId())).body(newExpense);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
    if (!expenseRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    expense.setId(id);
    Expense updatedExpense = expenseRepository.save(expense);
    return ResponseEntity.ok(updatedExpense);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
    if (!expenseRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    expenseRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
