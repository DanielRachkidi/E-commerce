package com.commerce.user.service;

import com.amazonaws.services.connect.model.UserNotFoundException;
import com.commerce.datamodel.User;
import com.commerce.datamodel.UserInfo;
import com.commerce.datamodel.UserPayment;
import com.commerce.user.repository.UserInfoRepository;
import com.commerce.user.repository.UserPaymentRepository;
import com.commerce.user.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
  
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  
  @Autowired
  private UserRepository repository;
  
  @Autowired
  private UserPaymentRepository userPaymentRepository;
  
  @Autowired
  private UserInfoRepository userInfoRepository;
  
  // register the user in the database
  public User signUp(User user)
  {
    try
    {
      
      if (repository.findBy(user.getUsername(), user.getPassword()).isEmpty())
      {
        
        return repository.save(user);
      }
      if (user.getPassword().isEmpty())
      {
        throw new PersistenceException("Password must not be null");
      }
      else if (user.getUsername().isEmpty())
      {
        throw new PersistenceException("Username must not be null");
      }
      else
      {
        throw new EntityExistsException("User already exists");
      }
    }
    catch (PersistenceException e)
    {
      String errorMessage = e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }
  
  // find the user by username and password in the login page
  public List<User> findByCredentials(String username, String password)
  {
    if (username.isEmpty() || password.isEmpty())
    {
      return Collections.emptyList();
    }
    return repository.findByCredentials(username, password);
  }
  
  public Optional<User> findById(int userId)
  {
    return repository.findById(userId);
  }
  
  //using it for the userRole controller
  public User getUserById(int userId)
  {
    return repository.findById(userId).orElse(null);
  }
  
  // the exception method for the payment attributes
  public boolean validatePayment(UserPayment userPayment)
  {
    // Perform the payment information validation logic here
    // Return true if the payment information is valid, false otherwise
    
    // Example validation logic
    if (userPayment.getPayment_type() == null || userPayment.getPayment_type().isEmpty())
    {
      return false;
    }
    if (userPayment.getProvider() == null || userPayment.getProvider().isEmpty())
    {
      return false;
    }
    
    if (userPayment.getExpiry() == null)
    {
      return false;
    }
    
    return true;
  }
  
  // create user payment method for the user when he register
  public UserPayment CreateUserPaymentInfo(User user, UserPayment userPayment)
  {
    
    if (userPaymentRepository.findByUser(user))
    {
      throw new IllegalArgumentException("User payment information already exists for this user");
    }
    
    // Set the user for the userPayment object
    userPayment.setUser(user);
    
    // Set other properties of the userPayment object
    userPayment.setPayment_type(userPayment.getPayment_type());
    userPayment.setProvider(userPayment.getProvider());
    userPayment.setAccountno(userPayment.getAccountno());
    userPayment.setExpiry(userPayment.getExpiry());
    
    // Perform validation if needed
    if (!validatePayment(userPayment))
    {
      throw new RuntimeException("Invalid payment information");
    }
    
    return userPaymentRepository.save(userPayment);
  }
  
  // create user information  for the  user when he registered
  public UserInfo createUserInfo(User user, UserInfo userInfo)
  {
    
    if (userInfoRepository.existsByUser(user))
    {
      throw new IllegalArgumentException("User information already exists for this user");
    }
    
    userInfo.setUser(user);
    
    if (userInfo.getAddress() == null || userInfo.getAddress().isEmpty() || userInfo.getCity() == null ||
        userInfo.getCity().isEmpty() || userInfo.getCode_postal() <= 0 || userInfo.getCountry() == null ||
        userInfo.getCountry().isEmpty())
    {
      throw new IllegalArgumentException(" Information cannot be null");
    }
    
    return userInfoRepository.save(userInfo);
  }
  
  // update method the attribute for the model  class payment
  public UserPayment updateUserPayment(User user, UserPayment userPayment, UserPayment userPaymentDetails)
  {
    
    logger.info("Provided User ID: {}", user.getId());
    logger.info("UserPayment User ID: {}", userPayment.getUser().getId());
    
    if (userPayment.getUser().getId() != user.getId())
    {
      throw new IllegalArgumentException("Invalid user for the provided UserPayment");
    }
    userPayment.setPayment_type(userPaymentDetails.getPayment_type());
    userPayment.setProvider(userPaymentDetails.getProvider());
    userPayment.setAccountno(userPaymentDetails.getAccountno());
    userPayment.setExpiry(userPaymentDetails.getExpiry());
    
    if (!validatePayment(userPayment))
    {
      throw new RuntimeException("Invalid payment update information");
    }
    
    return userPaymentRepository.save(userPayment);
  }
  
  // update method the attribute for the model  class information
  public UserInfo updateUserInfo(User user, UserInfo userInfo, UserInfo userInfoDetails)
  {
    if (userInfo.getUser().getId() != user.getId())
    {
      throw new IllegalArgumentException("Invalid user for the provided UserPayment");
    }
    
    userInfo.setAddress(userInfoDetails.getAddress());
    userInfo.setCity(userInfoDetails.getCity());
    userInfo.setCode_postal(userInfoDetails.getCode_postal());
    userInfo.setCountry(userInfoDetails.getCountry());
    
    if (userInfo.getAddress() == null || userInfo.getAddress().isEmpty() || userInfo.getCity() == null ||
        userInfo.getCity().isEmpty() || userInfo.getCode_postal() == 0 || userInfo.getCountry() == null ||
        userInfo.getCountry().isEmpty())
    {
      throw new IllegalArgumentException(" Information cannot be updated");
    }
    
    return userInfoRepository.save(userInfo);
  }
  
  public User updateUser(int userId, User user)
  throws UserNotFoundException
  {
    // Perform necessary validations or business logic before updating the user
    
    // Check if the user exists
    User existingUser = repository
      .findById(userId)
      .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    
    // Update the user entity
    existingUser.setUsername(user.getUsername());
    existingUser.setPassword(user.getPassword());
    existingUser.setFirst_name(user.getFirst_name());
    existingUser.setLast_name(user.getLast_name());
    existingUser.setTelephone(user.getTelephone());
    
    // Validate the user data
    if (user.getUsername() == null || user.getUsername().isEmpty())
    {
      throw new IllegalArgumentException("Username cannot be empty");
    }
    // Save the updated user in the database
    User updatedUser = repository.save(existingUser);
    
    // Perform any additional processing or return the updated user entity
    return updatedUser;
  }
}
