package com.commerce.id;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId
  implements Serializable
{
  
  private int userId;
  private int roleId;
  
  public UserRoleId(int userId, int roleId)
  {
    this.userId = userId;
    this.roleId = roleId;
  }
  
  public int getUserId()
  {
    return userId;
  }
  
  public void setUserId(int userId)
  {
    this.userId = userId;
  }
  
  public int getRoleId()
  {
    return roleId;
  }
  
  public void setRoleId(int roleId)
  {
    this.roleId = roleId;
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    UserRoleId that = (UserRoleId) o;
    return userId == that.userId && roleId == that.roleId;
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hash(userId, roleId);
  }
}