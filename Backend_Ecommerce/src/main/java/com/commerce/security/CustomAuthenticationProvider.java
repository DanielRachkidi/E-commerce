package com.commerce.security;

import com.commerce.datamodel.User;
import com.commerce.dto.UserPrincipal;
import com.commerce.user.UserService;
import java.util.List;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthenticationProvider
  implements AuthenticationProvider
{
  @Autowired
  private UserService service;
  
  @Override
  public Authentication authenticate(Authentication authentication)
  throws AuthenticationException
  {
    String name = authentication.getName();
    String password = authentication.getCredentials().toString();
        String hashed = DigestUtils.sha256Hex(Optional.ofNullable(password).orElse(""));
    
    List<User> userList = service.findByCredentials(name, hashed);
    
    if (!userList.isEmpty())
    {
      UserPrincipal principal = new UserPrincipal(userList.get(0));
      return new UsernamePasswordAuthenticationToken(principal, hashed);
    }
    else
    {
      return null;
    }
  }
  
  @Override
  public boolean supports(Class<?> authentication)
  {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}