package com.commerce.user;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.commerce.datamodel.Role;
import com.commerce.datamodel.User;
import com.commerce.datamodel.UserRole;
import com.commerce.dto.UserRoleDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserRoleService {
//
//  @Autowired
//  private UserRoleRepository userRoleRepository;
//
//  @Autowired
//  private UserService userService;
//
//  @Autowired
//  private RoleService roleService;
//
//  public UserRole createUserRole(UserRoleDTO userRoleDTO) {
//    // Map the DTO to the entity and save it
//    UserRole userRole = new UserRole();
//    userRole.setUserId(userRoleDTO.getUserId());
//    userRole.setRoleId(userRoleDTO.getRoleId());
//    // Set other fields if necessary
//    return userRoleRepository.save(userRole);
//  }
//
//  public List<UserRole> getAllUserRoles() {
//    return userRoleRepository.findAll();
//  }
//
//  public void deleteUserRole(int userId, int roleId) {
//    // Retrieve the UserRole entity to delete
//    Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
//    if (optionalUserRole.isEmpty()) {
//      throw new NotFoundException("UserRole not found"); // Or any appropriate exception for not found case
//    }
//    UserRole userRole = optionalUserRole.get();
//
//    // Delete the UserRole
//    userRoleRepository.delete(userRole);
//  }
//}
  
  private static final Logger logger = LoggerFactory.getLogger(UserRoleService.class);
  
  @Autowired
  private UserRoleRepository userRoleRepository;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private RoleService roleService;
  
  public UserRole createUserRole(UserRoleDTO userRoleDTO) {
    logger.info("Creating a new UserRole with userId: {} and roleId: {}", userRoleDTO.getUserId(), userRoleDTO.getRoleId());
    
    UserRole userRole = new UserRole();
    userRole.setUserId(userRoleDTO.getUserId());
    userRole.setRoleId(userRoleDTO.getRoleId());
    
    // Set other fields if necessary
    
    UserRole createdUserRole = userRoleRepository.save(userRole);
    logger.info("UserRole created successfully. Role Id: {} and User Id: {}",
      createdUserRole.getRoleId() , createdUserRole.getUserId());
    
    return createdUserRole;
  }
  
  public List<UserRole> getAllUserRoles() {
    logger.info("Fetching all UserRoles");
    
    List<UserRole> userRoles = userRoleRepository.findAll();
    
    logger.info("Fetched {} UserRoles", userRoles.size());
    
    return userRoles;
  }
  
  public void deleteUserRole(int userId, int roleId) {
    logger.info("Deleting UserRole with userId: {} and roleId: {}", userId, roleId);
    
    Optional<UserRole> optionalUserRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
    if (optionalUserRole.isEmpty()) {
      throw new NotFoundException("UserRole not found");
    }
    UserRole userRole = optionalUserRole.get();
    
    userRoleRepository.delete(userRole);
    
    logger.info("UserRole deleted successfully");
  }
}