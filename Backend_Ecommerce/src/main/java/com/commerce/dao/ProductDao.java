//package com.commerce.dao;
//
//
//import com.commerce.datamodel.Product;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ProductDao {
//  private Connection connection;
//
//  public ProductDao(Connection connection) {
//    this.connection = connection;
//  }
//
//  public void create(Product product) throws SQLException
//  {
//
//    String sql = "INSERT INTO product (name, price) VALUES (?, ?)";
//    try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
//    {
//      statement.setString(1, product.getName());
//      statement.setDouble(2, product.getPrice());
//      int affectedRows = statement.executeUpdate();
//
//      if (affectedRows == 0)
//      {
//        throw new SQLException("Inserting product failed, no rows affected.");
//      }
//
//      try (ResultSet generatedKeys = statement.getGeneratedKeys())
//      {
//        if (generatedKeys.next())
//        {
//          int id = generatedKeys.getInt(1);
//          product.setId(id);
//        }
//        else
//        {
//          throw new SQLException("Inserting product failed, no ID obtained.");
//        }
//      }
//    }
//  }
//
//  public Product findById(int id) throws SQLException {
//    String sql = "SELECT id_product, name, price FROM product WHERE id_product = ?";
//    try (PreparedStatement statement = connection.prepareStatement(sql)) {
//      statement.setInt(1, id);
//      try (ResultSet result = statement.executeQuery()) {
//        if (result.next()) {
//          int productId = result.getInt("id_product");
//          String name = result.getString("name");
//          double price = result.getDouble("price");
//          Product product = new Product(name, price);
//          product.setId(productId);
//          return product;
//        } else {
//          return null;
//        }
//      }
//    }
//  }
//
//  public List<Product> findAll() throws SQLException {
//    List<Product> products = new ArrayList<>();
//    String sql = "SELECT * FROM product";
//    try (Statement statement = connection.createStatement()) {
//      try (ResultSet result = statement.executeQuery(sql)) {
//        while (result.next()) {
//          int id_product = result.getInt("id_product");
//          String name = result.getString("name");
//          double price = result.getDouble("price");
//          Product product = new Product(name, price);
//          product.setId(id_product);
//          products.add(product);
//        }
//      }
//    }
//    return products;
//  }
//  public void update(Product product) throws SQLException {
//    String sql = "UPDATE product SET name = ?, price = ? WHERE id_product = ?";
//    try (PreparedStatement statement = connection.prepareStatement(sql)) {
//      statement.setString(1, product.getName());
//      statement.setDouble(2, product.getPrice());
//      statement.setInt(3, product.getId());
//      statement.executeUpdate();
//    }
//  }
//
//  public void delete(int id) throws SQLException {
//    String sql = "DELETE FROM product WHERE id_product = ?";
//    try (PreparedStatement statement = connection.prepareStatement(sql)) {
//      statement.setInt(1, id);
//      statement.executeUpdate();
//    }
//  }
//}
//
//
//
//
//
//
//
//
//
