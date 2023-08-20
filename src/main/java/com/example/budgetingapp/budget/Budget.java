package com.example.budgetingapp.budget;

import com.example.budgetingapp.user.LoggedUser;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table
public class Budget {
  @Id
  @SequenceGenerator(name = "budget_sequence", sequenceName = "budget_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "budget_sequence")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private LoggedUser loggedUser;

  private String name;
  private BigInteger createdAt;
  private BigDecimal amount;
  private String color;

  public Budget() {
  }

  public Budget(Long id, LoggedUser loggedUser, String name, BigInteger createdAt, BigDecimal amount, String color) {
    this.id = id;
    this.loggedUser = loggedUser;
    this.name = name;
    this.createdAt = createdAt;
    this.amount = amount;
    this.color = color;
  }

  public Budget(LoggedUser loggedUser, String name, BigInteger createdAt, BigDecimal amount, String color) {
    this.loggedUser = loggedUser;
    this.name = name;
    this.createdAt = createdAt;
    this.amount = amount;
    this.color = color;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LoggedUser getUser() {
    return loggedUser;
  }

  public void setUser(LoggedUser loggedUser) {
    this.loggedUser = loggedUser;
  }

  public String getName() {
    return name;
  }

  public void setName(String budgetName) {
    this.name = budgetName;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "Budget{" +
      "id=" + id +
      ", user=" + loggedUser +
      ", budgetName='" + name + '\'' +
      ", amount=" + amount +
      '}';
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public BigInteger getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(BigInteger createdAt) {
    this.createdAt = createdAt;
  }
}
