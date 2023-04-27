package com.commerce.datamodel;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
