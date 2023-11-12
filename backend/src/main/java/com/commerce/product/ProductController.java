package com.commerce.product;

import com.commerce.exceptions.DuplicateException;
import com.commerce.exceptions.IntegerException;
import com.commerce.exceptions.ServiceException;
import com.commerce.datamodel.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200")
@Validated
public class ProductController
{
  
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
  
  @Autowired
  private ProductService productService;

  // insert and save producr
  
  @PostMapping(path = "/addproduct", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> addProduct(@RequestBody Product product) {
    try {
      logger.info("Adding product: {}", product);
      Product savedProduct = productService.insert(product);
      logger.info("Product added: {}", savedProduct);
      return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }  catch (DuplicateException e) {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    } catch (IntegerException e) {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    } catch (ServiceException e) {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
  
  
  // see all product
  @GetMapping(path = "/list")
  @ResponseBody
  List<Product> getAllProduct()
  {
    logger.info("Getting all products");
    List<Product> products = productService.getAllProducts();
    logger.info("Returning all products: {}", products);
    return products;
  }
  

  // the all product by Gender
  @GetMapping("/lists/{gender}")
  public ResponseEntity<List<Product>> getProductListByGender(@PathVariable String gender) {
    logger.info("Fetching products for gender: {}", gender);
    List<Product> products = productService.getProductsByGender(gender);
    logger.info("Found {} products for gender: {}", products.size(), gender);
    return new ResponseEntity<>(products, HttpStatus.OK);
  }
  
  // delete product by id
  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void  deleteProductById(@PathVariable int id)
  {
    try
    {
      logger.info("Deleting product with ID: {}", id);
      productService.deleteProduct(id);
      logger.info("Deleting product with ID: {}", id);
    } catch (Exception e){
      logger.error("Error deleting product with ID: {}", id, e);
    }
  }
  

  
  // update product by id
  @PutMapping("/products/{productId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody Product updatedProduct) {
    try {
      logger.info("Updating product with ID: {}, Updated product details: {}", productId, updatedProduct);
      Product savedProduct = productService.updateProduct(productId, updatedProduct);
      return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    } catch (IllegalArgumentException e) {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    } catch (EntityNotFoundException e) {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (Exception e) {
      String errorMessage = e.getMessage();
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("error", errorMessage);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
  // see the product by id
  @GetMapping("/{productId}")
  public ResponseEntity<Product> getProductById(@PathVariable int productId) {

    Product product = productService.getProductById(productId);
    
    if (product != null) {
      return ResponseEntity.ok(product);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}
