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
@Table(schema = "public", name = "shopping_session")
@Getter
@Setter
@Access(AccessType.FIELD)
public class ShoppingSession
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_session")
  private int id;
  
  @Column(name = "total")
  private int total;
  
  @OneToOne
  @JoinColumn(name = "id_user", referencedColumnName = "id_user")
  private User user;
}
