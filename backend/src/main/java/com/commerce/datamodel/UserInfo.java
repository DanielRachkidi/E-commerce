package com.commerce.datamodel;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
