package com.commerce.datamodel;

import java.util.Objects;

public class Stock
{
  private int id;
  private int quantity;
  
  public Stock(int quantity)
  {
    this.quantity = quantity;
  }
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getQuantity()
  {
    return quantity;
  }
  
  public void setQuantity(int quantity)
  {
    this.quantity = quantity;
  }
  
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    Stock stock = (Stock) o;
    return id == stock.id && quantity == stock.quantity;
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hash(id, quantity);
  }
  
  @Override
  public String toString()
  {
    return "Stock{" +
           "id=" + id +
           ", quantity=" + quantity +
           '}';
  }
  
}
