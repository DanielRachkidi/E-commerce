package com.commerce.dto;

import com.commerce.datamodel.User;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPrincipal
{
  private final int id;
  private final String username;
  private final Instant loginTime;
  
  public UserPrincipal(User user)
  {
    this.id = user.getId();
    this.username = user.getUsername();
    this.loginTime = Instant.now();
  
  }
  
  
  public String subject()
  {
    return String.format("%s,%s,%s", id, username, loginTime);
  }
  
  public static UserPrincipal fromSubject(String subject)
  {
    String[] split = subject.split(",");
    return new UserPrincipal((int) Long.parseLong(split[0]), split[1], Instant.parse(split[2]));
  }
  
  
}