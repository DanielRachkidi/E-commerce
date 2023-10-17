package com.commerce.user;

import com.commerce.datamodel.Role;
import com.commerce.datamodel.User;
import com.commerce.dto.UserRoleDTO;
import com.commerce.user.repository.RoleRepository;
import com.commerce.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.commerce.datamodel.UserRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user-roles")
public class UserRoleController {
  
  private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);
  
  @Autowired
  private UserRoleService userRoleService;
  
  @Autowired
  private UserRoleRepository userRoleRepository;
  
  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private RoleRepository roleRepository;
  
  @PostMapping
  public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) {
    logger.info("Creating a new UserRole: {}", userRoleDTO);
    
    UserRole createdUserRole = userRoleService.createUserRole(userRoleDTO);
    UserRoleDTO createdUserRoleDTO = new UserRoleDTO(createdUserRole.getUserId(), createdUserRole.getRoleId());
    
    logger.info("UserRole created successfully. Created UserRoleDTO: {}", createdUserRoleDTO);
    
    return new ResponseEntity<>(createdUserRoleDTO, HttpStatus.CREATED);
  }
  
  @GetMapping("/{userId}/{roleId}")
  public ResponseEntity<Map<String, String>> getUserRoleById(
    @PathVariable("userId") int userId,
    @PathVariable("roleId") int roleId
  ) {
    logger.info("Fetching UserRole with userId: {} and roleId: {}", userId, roleId);
    
    Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
    
    if (optionalUserRole.isEmpty()) {
      logger.warn("UserRole not found");
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    UserRole userRole = optionalUserRole.get();
    
    // Fetch the User information separately
    Optional<User> optionalUser = userRepository.findById(userRole.getUserId());
    if (optionalUser.isEmpty()) {
      logger.warn("User not found for UserRole with userId: {}", userRole.getUserId());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    User user = optionalUser.get();
    String username = user.getUsername();
    
    // Fetch the Role information separately
    Optional<Role> optionalRole = roleRepository.findById(userRole.getRoleId());
    if (optionalRole.isEmpty()) {
      logger.warn("Role not found for UserRole with roleId: {}", userRole.getRoleId());
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    Role role = optionalRole.get();
    String roleName = role.getRoleName();
    
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put("username", username);
    responseBody.put("roleName", roleName);
    
    logger.info("UserRole fetched successfully. ResponseBody: {}", responseBody);
    
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }
  
  @GetMapping
  public ResponseEntity<List<UserRole>> getAllUserRoles() {
    logger.info("Fetching all UserRoles");
    
    List<UserRole> userRoles = userRoleService.getAllUserRoles();
    
    logger.info("Fetched {} UserRoles", userRoles.size());
    
    return new ResponseEntity<>(userRoles, HttpStatus.OK);
  }
  
  @DeleteMapping("/{userId}/{roleId}")
  public ResponseEntity<Void> deleteUserRole(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId) {
    logger.info("Deleting UserRole with userId: {} and roleId: {}", userId, roleId);
    
    try {
      userRoleService.deleteUserRole(userId, roleId);
      
      logger.info("UserRole deleted successfully");
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      logger.error("Error occurred while deleting UserRole", e);
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
