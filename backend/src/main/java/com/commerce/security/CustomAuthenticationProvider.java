package com.commerce.security;

import com.commerce.datamodel.User;
import com.commerce.dto.UserPrincipal;
import com.commerce.user.service.UserService;
import java.util.List;
import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
  
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
      User user = userList.get(0);
      UserPrincipal principal = new UserPrincipal(user);
      //return new UsernamePasswordAuthenticationToken(principal, hashed, List.of(Role.USER));
      return new UsernamePasswordAuthenticationToken(principal, hashed, List.of());
    }
    else
    {
      logger.warn("Authentication failed for user {}", name);
      return null;
    }
  }
  
  @Override
  public boolean supports(Class<?> authentication)
  {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}