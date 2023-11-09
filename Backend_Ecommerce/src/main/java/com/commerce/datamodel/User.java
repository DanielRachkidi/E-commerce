package com.commerce.datamodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

@Entity
@Table(schema = "public", name = "user")
@Getter
@Setter
@Access(AccessType.FIELD)
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user")
  private int id;
  
  @Column(name = "username")
  @Size(min = 3, message = "username should have min 3 characters")
  @NotBlank(message = "Name is required")
  private String username;
  
  @Column(name = "password")
  @Size(min = 6, message = "Password should have min 6 characters")
  @Pattern.List({
    @Pattern(regexp = ".*\\d.*", message = "Password should contain at least one digit"),
    @Pattern(regexp = ".*[a-z].*", message = "Password should contain at least one lowercase letter"),
//    @Pattern(regexp = ".*[A-Z].*", message = "Password should contain at least one uppercase letter")
  })
  private String password ;
  
  @Column(name = "first_name")
  private String first_name;
  
  @Column(name = "last_name")
  private String last_name;
  
  @Column(name = "telephone")
  private int telephone;
  
  
  @JsonIgnoreProperties("users")
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "userrole",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_role")
  )
  private Set<Role> roles = new HashSet<>();

  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getUsername()
  {
    return username;
  }
  
  
  public String getPassword()
  {
    return password;
  }
  
  
  @PrePersist
  @PreUpdate
  public void normalize() {
    if (password == null) {
      throw new PersistenceException("Password must not be null");
    }
    
    // Validate password against constraints
    if (password.length() < 6 || !password.matches(".*\\d.*") || !password.matches(".*[a-z].*")) {
      throw new PersistenceException("Invalid password. Password should have min 6 characters, contain at least one digit, and at least one lowercase letter");
    }
    
    // Hash the password
    password = DigestUtils.sha256Hex(password);
  }
}