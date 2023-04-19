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
