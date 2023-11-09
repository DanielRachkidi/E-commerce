package com.commerce.datamodel;


import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
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
  @NotBlank(message = "Name is required")
  private String name;
  @Column(name = "price")
  @NotNull(message = "Price is required")
  @PositiveOrZero(message = "Price cannot be negative")
  private Double price;
  
  
  @Column(name = "quantity")
  @NotNull(message = "Quantity is required")
  @Min(value = 0, message = "Quantity cannot be negative")
  private Integer quantity;
  
  @Column(name = "size")
  private String size;
  
  @Column(name = "gender")
  @Pattern(regexp = "^(man|women)$", message = "Invalid gender. Must be 'man' or 'women'")
  private String gender;
  
  
//  add SQL  quantity size and gender
  //quantity XS S M L XL
  //size
  // type man or women
  // positive
//  @Lob
//  @Column(columnDefinition = "MEDIUMBLOB")
//  private String image;
//  @ManyToOne
//  @JoinColumn(name = "id_stock", referencedColumnName = "id_stock")
//  private Stock stock;
  

//  public Product(String name, double price)
//  {
//    this.name = name;
//    this.price = price;
////    this.stock = stock;
//  }
  
  @Override
  public String toString()
  {
    return "Product{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", price=" + price +
           '}';
  }
  

}
