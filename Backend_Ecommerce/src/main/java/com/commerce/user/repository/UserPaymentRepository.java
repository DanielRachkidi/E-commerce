package com.commerce.user.repository;

import com.commerce.datamodel.User;
import com.commerce.datamodel.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserPaymentRepository
  extends JpaRepository<UserPayment, Integer>
{
  // Check if there are any UserPayment records associated with
  // a specific User if  it exists or not
  @Query("SELECT COUNT(u) > 0 FROM UserPayment u WHERE u.user = :user")
  boolean findByUser(@Param("user") User user);
  
  // Search for UserPayment records associated with a specific User
  @Query("SELECT u FROM UserPayment u WHERE u.user = :user")
  Optional<UserPayment> searchByUser(@Param("user") User user);
}
