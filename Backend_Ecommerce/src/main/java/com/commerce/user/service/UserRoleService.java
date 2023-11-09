package com.commerce.user.service;

import com.amazonaws.services.alexaforbusiness.model.NotFoundException;
import com.commerce.datamodel.UserRole;
import com.commerce.dto.UserRoleDTO;
import com.commerce.user.repository.UserRoleRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

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