package com.commerce.DAO;

import com.commerce.datamodel.Product1;
import com.commerce.datamodel.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao1
{
  private Connection connection;
  
  public ProductDao1(Connection connection) {
    this.connection = connection;
  }
  
  public int create(Product1 product) throws SQLException
  {
    String sql = "INSERT INTO product (name, price, id_stock) VALUES (?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getPrice());
      statement.setInt(3, product.getStock().getId());
      int affectedRows = statement.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Creating product failed, no rows affected.");
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          product.setId(id);
          return id;
        } else {
          throw new SQLException("Creating product failed, no ID obtained.");
        }
      }
    }
  }
  
  
  
  public Product1 findById(int id) throws SQLException {
    String sql = "SELECT p.id_product, p.name, p.price, s.id_stock , s.quantity FROM product p JOIN stock s ON p.id_stock = s.id_stock WHERE p.id_product = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      try (ResultSet result = statement.executeQuery()) {
        if (result.next()) {
          String name = result.getString("name");
          double price = result.getDouble("price");
          int stockId = result.getInt("id_stock");
          int stockQuantity = result.getInt("quantity");
          Stock stock = new Stock(stockQuantity);
          stock.setId(stockId);
          Product1 product = new Product1(name, price, stock);
          product.setId(id);
          return product;
        } else {
          return null;
        }
      }
    }
  }
  
  public List<Product1> findAll() throws SQLException {
    List<Product1> products = new ArrayList<>();
    String sql = "SELECT p.id_product, p.name, p.price, s.id_stock , s.quantity  FROM product p JOIN stock s ON p.id_stock = s.id_stock";
    try (Statement statement = connection.createStatement()) {
      try (ResultSet result = statement.executeQuery(sql)) {
        while (result.next()) {
          int id = result.getInt("id_product");
          String name = result.getString("name");
          double price = result.getDouble("price");
          int stockId = result.getInt("id_stock");
          int stockQuantity = result.getInt("quantity");
          Stock stock = new Stock( stockQuantity);
          stock.setId(stockId);
          Product1 product = new Product1(name, price, stock);
          product.setId(id);
          products.add(product);
        }
      }
    }
    return products;
  }
  
  public void update(Product1 product) throws SQLException {
    String sql = "UPDATE product SET name = ?, price = ?, id_stock = ? WHERE id_product = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, product.getName());
      statement.setDouble(2, product.getPrice());
      statement.setInt(3, product.getStock().getId());
      statement.setInt(4, product.getId());
      statement.executeUpdate();
    }
  }
  
  public void delete(int id) throws SQLException {
    String sql = "DELETE FROM product WHERE id_product = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      statement.executeUpdate();
    }
  }
}
