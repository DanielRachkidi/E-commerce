package com.commerce.security;

import com.commerce.dto.AuthRequest;
import com.commerce.dto.UserPrincipal;
import java.time.Instant;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
public class AuthApi
{
  private final CustomAuthenticationProvider provider;
  private final JwtEncoder jwtEncoder;
  
  @PostMapping("login")
  public ResponseEntity<UserPrincipal> login(@RequestBody @Valid AuthRequest request)
  {
    try
    {
      Authentication authentication = provider
        .authenticate(
          new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword()
          )
        );
  
      if (authentication == null)
      {
        // Handle null authentication object appropriately, e.g. return error response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
  
      String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
  
      UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
  
      Instant now = Instant.now();
      long expiry = 36000L;
  
      JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("com.commerce")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(user.subject())
        .claim("roles", scope) // Check for null and return empty string
        .build();
  
      String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  
      return ResponseEntity.ok()
        .header(HttpHeaders.AUTHORIZATION, token)
        .body(user);
    }
    catch (BadCredentialsException ex)
    {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  
  @PostMapping("logins")
  public ResponseEntity<UserPrincipal> login(@RequestBody @Valid AuthRequest request, HttpServletRequest httpRequest) {
    try {
      Authentication authentication = provider
        .authenticate(
          new UsernamePasswordAuthenticationToken(
            request.getUsername(), request.getPassword()
          )
        );

      if (authentication == null) {
        // Handle null authentication object appropriately, e.g. return error response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));

      UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

      Instant now = Instant.now();
      long expiry = 36000L;

      JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("com.commerce")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(user.subject())
        .claim("roles", scope) // Check for null and return empty string
        .build();

      String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();




      // Associate token with session on server side
      HttpSession session = httpRequest.getSession();
      session.setAttribute("token", token);

      // Set token in response header
      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.AUTHORIZATION, token);
//      System.out.println("hello user " + user.getUsername());

      return ResponseEntity.ok()
        .headers(headers)
        .body(user);
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

}