package com.commerce.datamodel;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "public", name = "payment_detail")
@Getter
@Setter
@Access(AccessType.FIELD)
public class PaymentDetail
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_payment_detail")
  private int id;
  
  @Column(name = "amount")
  private int amount;
  
  @Column(name = "provider")
  private int provider;
  
  @OneToOne
  @JoinColumn(name = "id_order_detail", referencedColumnName = "id_order_detail")
  private   OrderDetail orderDetail;
}
