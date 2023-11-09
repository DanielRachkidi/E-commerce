//package com.commerce.security;
//
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Component
//public class JwtTokenFilter
//  extends OncePerRequestFilter
//{
//  private JwtDecoder jwtDecoder;
//  private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//  throws ServletException, IOException
//  {
//    try
//    {
//      String token = extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
//      if (token != null && this.jwtDecoder.decode(token) != null)
//      {
//        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(Jwt.withTokenValue(token).build());
//        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
//      }
//    }
//    catch (JwtException e)
//    {
//      logger.warn("Invalid JWT token", e);
//    }
//    filterChain.doFilter(request, response);
//  }
//
//  private String extractToken(String header)
//  {
//    if (header == null || !header.startsWith("Bearer "))
//    {
//      return null;
//    }
//    return header.substring(7);
//  }
//}
