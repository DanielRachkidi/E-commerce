package com.commerce.datamodel;


import com.amazonaws.thirdparty.jackson.annotation.JsonIgnore;
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
  
  @Column(name = "accountno")
  private int accountno;
  
  @Column(name = "expiry")
  private Date expiry;
  
  @ManyToOne
  @JoinColumn(name = "id_user", referencedColumnName = "id_user")
  private User user;
  
}
