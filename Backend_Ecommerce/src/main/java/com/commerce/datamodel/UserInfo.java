package com.commerce.datamodel;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "public", name = "userinfo")
@Getter
@Setter
@Access(AccessType.FIELD)
public class UserInfo
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_userinfo")
  private int id;
  
  @Column(name = "address")
  private String address;
  
  @Column(name = "city")
  private String city;
  
  @Column(name = "code_postal")
  private int code_postal;
  
  @Column(name = "country")
  private String country;
  
  @ManyToOne
  @JoinColumn(name = "id_user", referencedColumnName = "id_user")
  private User user;
}
