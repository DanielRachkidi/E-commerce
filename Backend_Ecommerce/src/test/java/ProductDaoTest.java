import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.commerce.DAO.DatabaseManager;
import com.commerce.DAO.ProductDao;
import com.commerce.datamodel.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductDaoTest
{
  
  private Connection connection;
    private ProductDao productDao;
  
  @BeforeEach
    public void setUp() throws SQLException
    {
      // Set up a connection to the test database
      DatabaseManager dbManager = new DatabaseManager("jdbc:postgresql://localhost:5432/Ecommerce_Stock", "postgres", "wissam");
      connection = dbManager.getConnection();

      // Create a new instance of the ProductDao and pass in the test database connection
      this.productDao = new ProductDao(connection);
    }

    @After("")
    public void tearDown() throws SQLException {
      // Roll back any changes made to the test database
      connection.rollback();
      connection.close();
    }

    @Test
    public void testCreate() throws SQLException {
      // Create a new product
      Product product = new Product("mmmmm", 19.99);
      productDao.create(product);

      // Verify that the product was inserted into the database
      Product retrievedProduct = productDao.findById(product.getId());
  assertEquals(product, retrievedProduct);
  assertNotNull(product.getId());
  
    }
//
  @Test
  public void testFindById() throws SQLException {
    // Insert a new product into the database
  
    Product product = new Product("nino", 19.99);
    productDao.create(product);

    // Retrieve the same product from the database
    Product retrievedProduct = productDao.findById(product.getId());

    // Verify that the retrieved product is the same as the original product
    assertEquals(product, retrievedProduct);
    }
//
//  @Test
//  public void testFindAll() throws SQLException {
//    // Insert a few products into the database
//    Product product1 = new Product("nreeji", 19.99);
//    Product product2 = new Product("Gzezeifsdzmo", 9.99);
//    productDao.create(product1);
//    productDao.create(product2);
//
//    // Retrieve all products from the database
//    List<Product> products = productDao.findAll();
//
//    // Verify that the retrieved products are the same as the original products
//    assertTrue(products.contains(product1));
//    assertTrue(products.contains(product2));
//    }
  
  @Test
  public void testFindAll() throws SQLException {
    // Insert a few products into the database
    Product product1 = new Product("nrdsqxceji", 19.99);
    Product product2 = new Product("Gzeqcxzeifsdzmo", 9.99);
    productDao.create(product1);
    productDao.create(product2);
    
    // Retrieve all products from the database
    List<Product> products = productDao.findAll();
    
    // Print the list of products for debugging
    for (Product p : products) {
      System.out.println(p);
    }
    
    // Verify that the retrieved products are the same as the original products
    assertTrue(products.contains(product1));
    assertTrue(products.contains(product2));
  }

  @Test
  public void testUpdate() throws SQLException {
    // Insert a new product into the database
    Product product = new Product( "Widgkllkllet", 19.99);
    productDao.create(product);

    // Update the product's name and price
    product.setName("Gad,;,;;get");
    product.setPrice(29.99);
    productDao.update(product);

    // Retrieve the updated product from the database
    Product retrievedProduct = productDao.findById(product.getId());

    // Verify that the retrieved product is the same as the updated product
    assertEquals(product, retrievedProduct);
    }

  @Test
  public void testDelete() throws SQLException {
    // Insert a new product into the database
    Product product = new Product( "Widkkkget", 19.99);
    productDao.create(product);

    // Delete the product from the database
    productDao.delete(product.getId());

    // Verify that the product was deleted from the database
    Product retrievedProduct = productDao.findById(product.getId());
    assertNull(retrievedProduct);
    }
}
