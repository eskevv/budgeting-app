package com.example.budgetingapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

  private final IUserRepository IUserRepository;

  @Autowired
  public UserController(IUserRepository IUserRepository) {
    this.IUserRepository = IUserRepository;
  }

  @GetMapping
  public ResponseEntity<List<LoggedUser>> getAllUsers() {
    List<LoggedUser> loggedUsers = IUserRepository.findAll();
    return ResponseEntity.ok(loggedUsers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<LoggedUser> getUserById(@PathVariable Long id) {
    Optional<LoggedUser> user = IUserRepository.findById(id);
    return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<LoggedUser> createUser(@RequestBody LoggedUser loggedUser) {
    LoggedUser newLoggedUser = IUserRepository.save(loggedUser);
    return ResponseEntity.created(URI.create("/api/users/" + newLoggedUser.getId())).body(newLoggedUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<LoggedUser> updateUser(@PathVariable Long id, @RequestBody LoggedUser loggedUser) {
    if (!IUserRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    loggedUser.setId(id);
    LoggedUser updatedLoggedUser = IUserRepository.save(loggedUser);
    return ResponseEntity.ok(updatedLoggedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    if (!IUserRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }

    IUserRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

