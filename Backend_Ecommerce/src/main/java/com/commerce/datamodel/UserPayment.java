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
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "public", name = "userpayment")
@Getter
@Setter
@Access(AccessType.FIELD)
public class UserPayment
{
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_payment")
  private int id;
  
  @Column(name = "payment_type")
  private String payment_type;
  
  @Column(name = "provider")
  private String provider;
  
  @Column(name = "amount")
  private int amount;
  
  @Column(name = "expiry")
  private Date expiry;
  
  @ManyToOne
  @JoinColumn(name = "id_user", referencedColumnName = "id_user")
  private User user;
  
}
