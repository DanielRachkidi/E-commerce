//package com.commerce.dto;
//
//import org.springframework.security.core.GrantedAuthority;
//
//public record Role(String authority)
//  implements GrantedAuthority
//{
//  public static final String ADMIN_AUTH = "ADMIN";
//  public static final String USER_AUTH = "USER";
//
//  public static final Role ADMIN = new Role(ADMIN_AUTH);
//
//  public static final Role USER = new Role(USER_AUTH);
//
//  @Override
//  public String getAuthority()
//  {
//    return authority;
//  }
//}