package com.commerce.product;

import com.commerce.datamodel.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
  extends JpaRepository<Product, Integer>
{
  List<Product> findByGender(String gender);
}
