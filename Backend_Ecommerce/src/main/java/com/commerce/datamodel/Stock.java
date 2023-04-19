package com.commerce.datamodel;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "public", name = "stock")
@Getter
@Setter
@Access(AccessType.FIELD)
public class Stock
{
  
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_stock")
  private int id;
  @Column(name = "quantity")
  private int quantity;
  
  public Stock(int quantity)
  {
    this.quantity = quantity;
  }
  
  public Stock()
  {
  
  }
}
































//  private int id;
//  private int quantity;
//
//  public Stock(int quantity)
//  {
//    this.quantity = quantity;
//  }
//
//  public int getId()
//  {
//    return id;
//  }
//
//  public void setId(int id)
//  {
//    this.id = id;
//  }
//
//  public int getQuantity()
//  {
//    return quantity;
//  }
//
//  public void setQuantity(int quantity)
//  {
//    this.quantity = quantity;
//  }
//
//  @Override
//  public boolean equals(Object o)
//  {
//    if (this == o)
//    {
//      return true;
//    }
//    if (o == null || getClass() != o.getClass())
//    {
//      return false;
//    }
//    Stock stock = (Stock) o;
//    return id == stock.id && quantity == stock.quantity;
//  }
//
//  @Override
//  public int hashCode()
//  {
//    return Objects.hash(id, quantity);
//  }
//
//  @Override
//  public String toString()
//  {
//    return "Stock{" +
//           "id=" + id +
//           ", quantity=" + quantity +
//           '}';
//  }

