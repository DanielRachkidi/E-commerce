package com.commerce.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Getconnection
{
  public static Connection connection;
  public static Connection getConnection()

  {
  
    try
    {
      connection = DriverManager.getConnection(
        "jdbc:postgresql://localhost:5432/Ecommerce_Stock",
        "postgres",
        "wissam"
      );
      System.out.println("run successfully....");
      return connection;
    }
    catch (SQLException e)
    {
    
      e.printStackTrace();
    }
    return null;
  }
  
  public static void main(String[] args)
  {
    getConnection();
  }
  

  }

