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
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "public", name = "order_detail")
@Getter
@Setter
@Access(AccessType.FIELD)
public class OrderDetail
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_order_detail")
  private int id;
  
  @OneToOne
  @JoinColumn(name = "id_user", referencedColumnName = "id_user")
  private User user;
  
  @OneToOne
  @JoinColumn(name = "id_payment_detail", referencedColumnName = "id_payment_detail")
  private   PaymentDetail paymentDetail;
  
}
