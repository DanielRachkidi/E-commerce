package com.commerce.DAO;

import com.commerce.datamodel.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StockDao
{
  private final Connection connection;
  
  public StockDao(Connection connection)
  {
    this.connection = connection;
  }
  
  public void create(Stock stock)
  throws SQLException
  {
    String sql = "INSERT INTO stock ( quantity) VALUES (?)";
    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
    {
      statement.setInt(1, stock.getQuantity());
      int affectedRows = statement.executeUpdate();
    
      if (affectedRows == 0)
      {
        throw new SQLException("Inserting product failed, no rows affected.");
      }
    
      try (ResultSet generatedKeys = statement.getGeneratedKeys())
      {
        if (generatedKeys.next())
        {
          int id = generatedKeys.getInt(1);
          stock.setId(id);
        }
        else
        {
          throw new SQLException("Inserting product failed, no ID obtained.");
        }
      }
    }
  }
  
  public Stock findById(int id)
  throws SQLException
  {
    Stock stock = null;
    String sql = "SELECT * FROM stock WHERE id_stock = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, id);
      try (ResultSet result = statement.executeQuery())
      {
        if (result.next())
        {
          int quantity = result.getInt("quantity");
          stock = new Stock(quantity);
          stock.setId(id);
        }
      }
    }
    return stock;
  }
  
  public List<Stock> findAll()
  throws SQLException
  {
    List<Stock> stocks = new ArrayList<>();
    String sql = "SELECT * FROM stock";
    try (Statement statement = connection.createStatement())
    {
      try (ResultSet result = statement.executeQuery(sql))
      {
        while (result.next())
        {
          int id = result.getInt("id_stock");
          int quantity = result.getInt("quantity");
          Stock stock = new Stock(quantity);
          stock.setId(id);
          stocks.add(stock);
        }
      }
    }
    return stocks;
  }
  
  public void update(Stock stock)
  throws SQLException
  {
    String sql = "UPDATE stock SET  quantity = ? WHERE id_stock = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, stock.getQuantity());
      statement.setInt(2, stock.getId());
      statement.executeUpdate();
    }
  }
  
  public void delete(int id)
  throws SQLException
  {
    String sql = "DELETE FROM stock WHERE id_stock = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql))
    {
      statement.setInt(1, id);
      statement.executeUpdate();
    }
  }
}
