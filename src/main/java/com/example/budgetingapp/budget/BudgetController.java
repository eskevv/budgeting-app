package com.example.budgetingapp.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/budgets")
public class BudgetController {

  private final IBudgetRepository IBudgetRepository;

  @Autowired
  public BudgetController(IBudgetRepository IBudgetRepository) {
    this.IBudgetRepository = IBudgetRepository;
  }

  @GetMapping
  public ResponseEntity<List<Budget>> getAllBudgets() {
    List<Budget> aBudgets = IBudgetRepository.findAll();
    return ResponseEntity.ok(aBudgets);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Budget> getBudgetById(@PathVariable java.lang.Long id) {
    Optional<Budget> budget = IBudgetRepository.findById(id);
    return budget.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Budget> createBudget(@RequestBody Budget aBudget) {
    Budget newBudget = IBudgetRepository.save(aBudget);
    return ResponseEntity.created(URI.create("/api/budgets/" + newBudget.getId())).body(newBudget);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Budget> updateBudget(@PathVariable java.lang.Long id, @RequestBody Budget aBudget) {
    if (!IBudgetRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    aBudget.setId(id);
    Budget updatedBudget = IBudgetRepository.save(aBudget);
    return ResponseEntity.ok(updatedBudget);
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteBudgets() {
    IBudgetRepository.deleteAll();
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBudget(@PathVariable java.lang.Long id) {
    if (!IBudgetRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    IBudgetRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
