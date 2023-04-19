package com.commerce.user;

import com.commerce.datamodel.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController
{
  @Autowired
  private UserRepository userRepository;
  
  @GetMapping
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
  
  @PostMapping(consumes = {"application/json"})
  public User createUser(@RequestBody User user) {
    return userRepository.save(user);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      updatedUser.setId(id);
      User savedUser = userRepository.save(updatedUser);
      return ResponseEntity.ok(savedUser);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      userRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  
}
