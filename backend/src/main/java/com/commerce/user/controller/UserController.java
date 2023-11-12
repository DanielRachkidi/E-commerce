package com.commerce.user.controller;

import com.amazonaws.services.accessanalyzer.model.ResourceNotFoundException;
import com.amazonaws.services.connect.model.UserNotFoundException;
import com.commerce.datamodel.Role;
import com.commerce.datamodel.User;
import com.commerce.datamodel.UserInfo;
import com.commerce.datamodel.UserPayment;
import com.commerce.dto.UserPrincipal;
import com.commerce.dto.UserView;
import com.commerce.user.repository.RoleRepository;
import com.commerce.user.repository.UserInfoRepository;
import com.commerce.user.repository.UserPaymentRepository;
import com.commerce.user.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.PersistenceException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Validated
public class UserController
{
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private JwtDecoder jwtDecoder;
  
  @Autowired
  private UserInfoRepository userInfoRepository;
  
  @Autowired
  private UserPaymentRepository userPaymentRepository;
  
  @Autowired
  private RoleRepository roleRepository;
  
  // registration or create a signup to the user by inserting a role "user"
  @PostMapping(path = "/public/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> signUp(@RequestBody User user)
  {
    try
    {
      logger.info("Received request to sign up user {}", user.getUsername());
      
      // Check if the default role exists
      Role defaultRole = roleRepository.findByRoleName("user");
      if (defaultRole == null)
      {
        // Create the default role if it doesn't exist
        defaultRole = new Role();
        defaultRole.setRoleName("user");
        roleRepository.save(defaultRole);
      }
      
      // Assign the default role to the user
      user.getRoles().add(defaultRole);
      
      User createdUser = userService.signUp(user);
      UserView userView = new UserView(createdUser);
      return new ResponseEntity<>(userView, HttpStatus.CREATED);
    }
    catch (PersistenceException e)
    {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
  
  // by login get token in the header and by manually  add it in the current user
  @GetMapping("/public/user/current")
  public UserPrincipal findCurrent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token)
  {
    logger.info("Received request to find current user");
    return UserPrincipal.fromSubject(jwtDecoder.decode(token.replace("Bearer ", "")).getSubject());
  }
  
  //insert or create user payment
  
  @PostMapping("/public/{userId}/userpayment")
  public ResponseEntity<UserPayment> createUserPayment(
    @PathVariable(value = "userId") int userId,
    @Valid @RequestBody UserPayment userPayment
  )
  throws ResourceNotFoundException
  {
    User user = userService.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    
    UserPayment savedUserPayment = userService.CreateUserPaymentInfo(user, userPayment);
    
    // Call the updateUserPaymentInfo method
    
    logger.info("Payment has been successfully created by user {}", user.getUsername());
    
    return ResponseEntity.ok(savedUserPayment);
  }
  
  //insert or create user information
  @PostMapping("/public/{userId}/userinfo")
  public ResponseEntity<UserInfo> createUserInfo(
    @PathVariable(value = "userId") int userId,
    @Valid @RequestBody UserInfo userInfo
  )
  throws ResourceNotFoundException
  {
    User user = userService.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    
    UserInfo savedUserInfo = userService.createUserInfo(user, userInfo);
    
    logger.info("Information has been successfully created by user {}", user.getUsername());
    
    return ResponseEntity.ok(savedUserInfo);
  }
  
  // update the payment
  @PutMapping("/public/{userId}/userpayment/{paymentId}")
  public ResponseEntity<UserPayment> updateUserPayment(
    @PathVariable(value = "userId") int userId,
    @PathVariable(value = "paymentId") int paymentId,
    @Valid @RequestBody UserPayment userPaymentDetails
  )
  throws ResourceNotFoundException
  {
    User user = userService.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    
    UserPayment userPayment = userPaymentRepository.findById(paymentId)
      .orElseThrow(() -> new ResourceNotFoundException("UserPayment not found for this id: " + paymentId));
    
    UserPayment updatedPayment = userService.updateUserPayment(user, userPayment, userPaymentDetails);
    
    logger.info("Payment has been successfully updated by user {}", user.getUsername());
    
    return ResponseEntity.ok(updatedPayment);
  }
  
  // update userinfo of the user id and his id
  @PutMapping("/public/{userId}/userinfo/{infoId}")
  public ResponseEntity<UserInfo> updateUserInfo(
    @PathVariable(value = "userId") int userId,
    @PathVariable(value = "infoId") int infoId,
    @Valid @RequestBody UserInfo userInfoDetails
  )
  throws ResourceNotFoundException
  {
    User user = userService.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    
    UserInfo userInfo = userInfoRepository.findById(infoId)
      .orElseThrow(() -> new ResourceNotFoundException("UserInfo not found for this id: " + infoId));
    
    UserInfo updatedUserInfo = userService.updateUserInfo(user, userInfo, userInfoDetails);
    
    logger.info("Info has been successfully updated by user {}", user.getUsername());
    
    return ResponseEntity.ok(updatedUserInfo);
  }
  
  // update user by his id
  @PutMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> updateUser(@PathVariable("id") int userId, @RequestBody User user)
  {
    logger.info("Received request to update user with ID {}", userId);
    
    try
    {
      User updatedUser = userService.updateUser(userId, user);
      return ResponseEntity.ok(updatedUser);
    }
    catch (UserNotFoundException e)
    {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    catch (IllegalArgumentException e)
    {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }
  
  // Get user payment
  @GetMapping("/userpayment/{userId}")
  public ResponseEntity<UserPayment> getUserPayment(@PathVariable(value = "userId") int userId)
  throws ResourceNotFoundException
  {
    User user = userService.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    UserPayment userPayment = userPaymentRepository.searchByUser(user)
      .orElseThrow(() -> new ResourceNotFoundException("UserPayment not found for this user"));
    return ResponseEntity.ok(userPayment);
  }
  
  // Get user information
  @GetMapping("/userinfo/{userId}")
  public ResponseEntity<UserInfo> getUserInfo(@PathVariable int userId)
  throws ResourceNotFoundException
  {
    User user = userService.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    UserInfo userInfo = userInfoRepository.findByUser(user)
      .orElseThrow(() -> new ResourceNotFoundException("UserInfo not found for this user"));
    return ResponseEntity.ok(userInfo);
  }
}

