package com.example.budgetingapp.user;

import jakarta.persistence.*;

@Entity
@Table
public class LoggedUser {
  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  private Long id;
  private String username;
  private String email;

  public LoggedUser() {
  }

  public LoggedUser(String username, String email) {
    this.username = username;
    this.email = email;
  }

  public LoggedUser(Long id, String username, String email) {
    this.id = id;
    this.username = username;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      '}';
  }
}
