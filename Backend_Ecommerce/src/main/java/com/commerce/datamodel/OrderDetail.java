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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
