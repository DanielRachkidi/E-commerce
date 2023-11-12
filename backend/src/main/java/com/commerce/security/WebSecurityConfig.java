package com.commerce.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig
{
  private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
  
  @Value("${jwt.public.key}")
  private RSAPublicKey rsaPublicKey;
  
  @Value("${jwt.private.key}")
  private RSAPrivateKey rsaPrivateKey;
  


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http)
  throws Exception
  {
    logger.info("Setting filter chain...");
    // Enable CORS and disable CSRF
    http = http.cors().and().csrf().disable();
    
    http = http
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and();
    
    http
      .logout(logout -> {LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer = logout;}
      );
  
  
    // Set permissions on endpoints and not having 401 Unauthorized
    http
      .authorizeHttpRequests((auth) -> auth
        .antMatchers("/users/**").permitAll()
        .antMatchers("/api/user/logins").permitAll()
        .antMatchers("/users/user/current").permitAll()
        .antMatchers("/product/**").permitAll()
        .antMatchers("/api/**").permitAll()
        .antMatchers("/admin/**").permitAll()
        .antMatchers("/user-roles/**").permitAll()
  
  
  
  
  
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer()
      .jwt();
  
  
    // Set unauthorized requests exception handler
    http
      .exceptionHandling((exceptions) -> exceptions
        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
        .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));
  
    logger.info("Filter chain successfully set up");
    return http.build();
  }
  
  @Bean
  public JwtEncoder jwtEncoder()
  {
    JWK jwk = new RSAKey.Builder(this.rsaPublicKey).privateKey(this.rsaPrivateKey).build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwkSource);
  }
  
  // Used by JwtAuthenticationProvider to decode and validate JWT tokens
  @Bean
  public JwtDecoder jwtDecoder()
  {
    return NimbusJwtDecoder.withPublicKey(this.rsaPublicKey).build();
  }
  
  // Extract authorities from the roles claim
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter()
  {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
    
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }
  
  

  @Bean
  public CorsFilter corsFilter()
  {
    //
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(false);
    config.addAllowedOriginPattern("*");
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.addExposedHeader(HttpHeaders.AUTHORIZATION); // for the token in the front end show
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    return new CorsFilter(source);
  }
}
