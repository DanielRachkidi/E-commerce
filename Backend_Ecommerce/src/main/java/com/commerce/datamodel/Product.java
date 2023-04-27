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
@Table(schema = "public", name = "product")
@Getter
@Setter
@Access(AccessType.FIELD)
public class Product
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_product")
  private int id;
  @Column(name = "name")
  private String name;
  @Column(name = "price")
  private Double price;
  
  @ManyToOne
  @JoinColumn(name = "id_stock", referencedColumnName = "id_stock")
  private Stock stock;
  

  public Product(String name, double price, Stock stock)
  {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }
  
  public Product()
  {
  
  }
}
