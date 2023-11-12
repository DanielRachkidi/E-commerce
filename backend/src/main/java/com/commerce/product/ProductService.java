package com.commerce.product;

import com.commerce.datamodel.Product;
import com.commerce.exceptions.DuplicateException;
import com.commerce.exceptions.IntegerException;
import com.commerce.exceptions.ServiceException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProductService
{
  @Autowired
  ProductRepository productRepository;
  
  // read list of the product
  public List<Product> getAllProducts()
  {
    return productRepository.findAll();
  }
  
  //add product in the list
  public Product insert(Product product)
  {
    try
    {
      return productRepository.save(product);
    }
    catch (DataIntegrityViolationException e)
    {
      throw new DuplicateException("Duplicate key error: " + e.getMessage());
    }
    catch (IllegalArgumentException e)
    {
      throw new IntegerException("Negative number error: " + e.getMessage());
    }
    catch (Exception e)
    {
      throw new ServiceException(e.getMessage());
    }
  }
  
  // see and read specific product from the list
  public Product getProductById(int id)
  {
    return productRepository.findById(id).orElse(null);
  }
  
  // delete specific product by id
  public void deleteProduct(int id)
  {
    try
    {
      productRepository.deleteById(id);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  // update product  by id
  public Product updateProduct(int productId, Product updatedProduct)
  {
    Product existingProduct = productRepository.findById(productId).orElse(null);
    if (existingProduct != null)
    {
      if (updatedProduct.getPrice() < 0)
      {
        throw new ServiceException("Price cannot be negative");
      }
      existingProduct.setName(updatedProduct.getName());
      existingProduct.setPrice(updatedProduct.getPrice());
      existingProduct.setQuantity(updatedProduct.getQuantity());
      existingProduct.setSize(updatedProduct.getSize());
      existingProduct.setGender(updatedProduct.getGender());
      
      return productRepository.save(existingProduct);
    }
    throw new ServiceException("Product not found with ID: " + productId);
  }
  
  // specify the list of products by the  attribute gender
  public List<Product> getProductsByGender(String gender)
  {
    return productRepository.findByGender(gender);
  }
}
