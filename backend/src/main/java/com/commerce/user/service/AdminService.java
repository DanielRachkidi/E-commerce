package com.commerce.user.service;

import com.commerce.datamodel.User;
import com.commerce.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{
  @Autowired
  private UserRepository repository;
  
  // create user Admin
  public User signUpAdmin(User user)
  {
    
    if (repository.findBy(user.getUsername(), user.getPassword()).isEmpty())
    {
      
      return repository.save(user);
    }
    if (user.getPassword().isEmpty())
    {
      throw new PersistenceException("Password must not be null");
    }
    else
    {
      throw new EntityExistsException("User already exists");
    }
  }
  
  // read all rows of the list
  public List<User> findAll()
  {
    return repository.findAll();
  }
  
  public Optional<User> findById(int userId)
  {
    return repository.findById(userId);
  }
}



