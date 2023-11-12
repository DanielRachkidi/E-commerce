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
