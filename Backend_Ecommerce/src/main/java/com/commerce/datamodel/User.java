package com.commerce.datamodel;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
  private String username;
  
  @Column(name = "password")
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
}
