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
@Table(schema = "public", name = "cart_product")
@Getter
@Setter
@Access(AccessType.FIELD)
public class CartProduct
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cart_product")
  private int id;
  
  @Column(name = "quantity")
  private int quantity;
  
  @ManyToOne
  @JoinColumn(name = "id_session", referencedColumnName = "id_session")
  private  ShoppingSession session;
  
  @OneToOne
  @JoinColumn(name = "id_product", referencedColumnName = "id_product")
  private   Product product;
  
}
