package com.example.budgetingapp.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBudgetRepository extends JpaRepository<Budget, Long> {
}
