package com.commerce.dto;

import com.commerce.datamodel.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserView
{
  private int id;
  private String username;
  
  private String password;
  
  
  
  public UserView(User user)
  {
    this.id = user.getId();
    this.username = user.getUsername();
    this.password = user.getPassword();
  }
  
  
}
