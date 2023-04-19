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
@Table(schema = "public", name = "order_list")
@Getter
@Setter
@Access(AccessType.FIELD)
public class OrderList
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_order_list")
  private int id;
  
  @Column(name = "quantity")
  private int quantity;
  
  @OneToOne
  @JoinColumn(name = "id_product", referencedColumnName = "id_product")
  private   Product product;
  
  @OneToOne
  @JoinColumn(name = "id_order_detail", referencedColumnName = "id_order_detail")
  private   OrderDetail orderDetail;
  
}
