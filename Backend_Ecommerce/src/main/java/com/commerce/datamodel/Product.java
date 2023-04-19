package com.commerce.datamodel;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Objects;
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
