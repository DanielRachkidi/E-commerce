package com.commerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDTO
{
  private int userId;
  private int roleId;
  
  public UserRoleDTO(int userId, int roleId)
  {
    this.userId = userId;
    this.roleId = roleId;
  }
}
