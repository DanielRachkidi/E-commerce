package com.commerce.datamodel;

import java.util.Objects;

public class Product
{
  private int id;
  private String name;
  private Double price;
  
  public Product(String name, Double price)
  {
    this.name = name;
    this.price = price;
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
  
  public Double getPrice()
  {
    return price;
  }
  
  public void setPrice(Double price)
  {
    this.price = price;
  }
  
  @Override
  public String toString()
  {
    return "Product{" +
           "id=" + id +
           ", Name='" + name + '\'' +
           ", price=" + price +
           '}';
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Product)) {
      return false;
    }
    Product other = (Product) obj;
    return id == other.id &&
           Objects.equals(name, other.name) &&
           Double.compare(price, other.price) == 0;
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(id, name, price);
  }
}
