package com.commerce.datamodel;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
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
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getFirst_name()
  {
    return first_name;
  }
  
  public void setFirst_name(String first_name)
  {
    this.first_name = first_name;
  }
  
  public String getLast_name()
  {
    return last_name;
  }
  
  public void setLast_name(String last_name)
  {
    this.last_name = last_name;
  }
  
  public int getTelephone()
  {
    return telephone;
  }
  
  public void setTelephone(int telephone)
  {
    this.telephone = telephone;
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