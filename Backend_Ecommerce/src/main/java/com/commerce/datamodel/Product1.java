package com.commerce.datamodel;

import java.util.Objects;

public class Product1
{
  private int id;
  private String name;
  private double price;
  private Stock stock;
  
  public Product1(String name, double price, Stock stock)
  {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }
  
  public int getId()
  {
    return id;
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public double getPrice()
  {
    return price;
  }
  
  public void setPrice(double price)
  {
    this.price = price;
  }
  
  public Stock getStock()
  {
    return stock;
  }
  
  public void setStock(Stock stock)
  {
    this.stock = stock;
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
    Product1 product1 = (Product1) o;
    return id == product1.id && Double.compare(product1.price, price) == 0 &&
           Objects.equals(name, product1.name) && Objects.equals(stock, product1.stock);
  }
  
  @Override
  public int hashCode()
  {
    return Objects.hash(id, name, price, stock);
  }
  
  @Override
  public String toString()
  {
    return "Product1{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", price=" + price +
           ", stock=" + stock +
           '}';
  }
}
