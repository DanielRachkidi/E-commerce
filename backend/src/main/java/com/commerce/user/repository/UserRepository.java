package com.commerce.user.repository;

import com.commerce.datamodel.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
  extends JpaRepository<User, Integer>
{
  @Query(value = "select r from User r where r.username = :username and r.password = :password")
  List<User> findByCredentials(@Param("username") String username, @Param("password") String password);
  
  @Query(value = "select r from User r where r.username = :username or r.password = :password")
  List<User> findBy(
    @Param("username") String username,
    @Param("password") String password
  );
}
