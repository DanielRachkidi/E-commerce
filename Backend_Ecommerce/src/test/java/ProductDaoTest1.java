import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.commerce.DAO.DatabaseManager;
import com.commerce.DAO.ProductDao;
import com.commerce.DAO.ProductDao1;
import com.commerce.DAO.StockDao;
import com.commerce.datamodel.Product;
import com.commerce.datamodel.Product1;
import com.commerce.datamodel.Stock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDaoTest1
{
  
  private Connection connection;
  private ProductDao1 productDao;
  private StockDao stockDao;
  
  @BeforeEach
  public void setUp() throws SQLException {
    // Set up a connection to a test database
    DatabaseManager dbManager = new DatabaseManager("jdbc:postgresql://localhost:5432/Ecommerce_Stock", "postgres", "wissam");
    connection = dbManager.getConnection();
    
    // Initialize DAO objects
    this.productDao = new ProductDao1(connection);
    this.stockDao = new StockDao(connection);
  }
  
  @After("")
  public void tearDown() throws SQLException {
    // Rollback any changes made during the test
    connection.rollback();
    
    // Close the connection
    connection.close();
  }
  
  @Test
  public void testCreateProduct() throws SQLException {
    // Create a new Stock object to associate with the product
    Stock stock = new Stock( 100);
    stockDao.create(stock);
    
    // Create a new Product object with the Stock object as its foreign key
    Product1 product = new Product1("Test Product", 9.99, stock);
    
    // Save the Product object to the database
    productDao.create(product);
    
    // Retrieve the Product object from the database
    Product1 retrievedProduct = productDao.findById(product.getId());
    
    // Assert that the retrieved Product object is equal to the original Product object
    assertEquals(product, retrievedProduct);
  }
  
  
    @Test
    public void testUpdateProduct() throws SQLException {
      // Create a new Stock object to associate with the product
      Stock stock = new Stock( 100);
      stockDao.create(stock);
  
      // Create a new Product object with the Stock object as its foreign key
      Product1 product = new Product1("Test Product", 9.99, stock);
  
      // Save the Product object to the database
      productDao.create(product);
  
      // Update the name and price of the Product object
      product.setName("New Product Name");
      product.setPrice(19.99);
  
      // Update the Product object in the database
      productDao.update(product);
  
      // Retrieve the updated Product object from the database
      Product1 retrievedProduct = productDao.findById(product.getId());
  
      // Assert that the retrieved Product object is equal to the updated Product object
      assertEquals(product, retrievedProduct);
    }
  @Test
  public void testDeleteProduct() throws SQLException {
    // Create a new Stock object to associate with the product
    Stock stock = new Stock(100);
    stockDao.create(stock);
    
    // Create a new Product object with the Stock object as its foreign key
    Product1 product = new Product1("Test Product", 9.99, stock);
    
    // Save the Product object to the database
    productDao.create(product);
    
    // Delete the Product object from the database
    productDao.delete(product.getId());
    
    // Retrieve the deleted Product object from the database
    Product1 retrievedProduct = productDao.findById(product.getId());
    
    // Assert that the retrieved Product object is null
    assertNull(retrievedProduct);
  }
  
  @Test
  public void testFindAll() throws SQLException {
    // Add some test products
    Stock stock = new Stock(100);
    stockDao.create(stock);
    Stock stock1 = new Stock(50);
    stockDao.create(stock1);
    Product1 product1 = new Product1("Product 1", 9.99, stock);
    Product1 product2 = new Product1("Product 2", 19.99, stock1);
    productDao.create(product1);
    productDao.create(product2);
    
    // Retrieve all products
    List<Product1> productList = productDao.findAll();
    
    // Check that the list contains the expected products
    assertEquals(2, productList.size());
    assertTrue(productList.contains(product1));
    assertTrue(productList.contains(product2));
  }
}
