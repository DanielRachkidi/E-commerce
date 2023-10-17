package com.commerce.user;

import com.commerce.datamodel.Role;
import com.commerce.datamodel.User;
import com.commerce.dto.UserView;
import com.commerce.user.repository.RoleRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController
{
  
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  
  @Autowired
  private AdminService adminService;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private RoleRepository roleRepository;
  
  @PostMapping(path = "/public/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> signUp(@RequestBody User user)
  {
    try
    {
      logger.info("Received request to sign up user {}", user.getUsername());
      
      // Check if the default role exists
      Role defaultRole = roleRepository.findByRoleName("admin");
      if (defaultRole == null)
      {
        // Create the default role if it doesn't exist
        defaultRole = new Role();
        defaultRole.setRoleName("admin");
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
  
  @GetMapping("/admin/{userId}")
  //  @RolesAllowed(Role.ADMIN_AUTH)
  public Optional<User> findById(@PathVariable(value = "userId") int userId)
  {
    logger.info("Received request to find user by ID {}", userId);
    return adminService.findById(userId);
  }
  
  @PostMapping(path = "/admin/signupAdmin", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserView signUpAdmins(@RequestBody User user)
  {
    logger.info("Received request to sign up admin user {}", user.getUsername());
    return new UserView(adminService.signUpAdmin(user));
  }
  
  @GetMapping("/admin/users")
  //  @RolesAllowed(Role.ADMIN_AUTH)
  public List<User> findAll()
  {
    logger.info("Received request to find all users");
    return adminService.findAll();
  }
}

