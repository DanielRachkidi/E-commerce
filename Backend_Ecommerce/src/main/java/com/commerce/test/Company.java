package com.commerce.test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Company")
public class Company
{
  @Id
  private int ID_comapny;
  private String Name;
  private  String Address;
}
