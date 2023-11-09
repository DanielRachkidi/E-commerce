package com.commerce.user.service;

import com.commerce.datamodel.Role;
import com.commerce.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  
  @Autowired
  private RoleRepository roleRepository;
  
  public Role getRoleById(int roleId) {
    return roleRepository.findById(roleId).orElse(null);
  }
  
  public Role getUserRole() {
    return roleRepository.findByRoleName("user");
  }
  
  public Role getAdminRole() {
    return roleRepository.findByRoleName("admin");
  }
  
  
}

