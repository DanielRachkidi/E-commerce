package com.commerce.user.repository;

import com.commerce.datamodel.User;
import com.commerce.datamodel.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository
  extends JpaRepository<UserInfo, Integer>
{
  // Check if there are any UserInfo records associated with
  // a specific User if  it exists or not
  @Query("SELECT COUNT(u) > 0 FROM UserInfo u WHERE u.user = :user")
  boolean existsByUser(@Param("user") User user);
  
  // Search for UserInfo records associated with a specific User
  @Query("SELECT u FROM UserInfo u WHERE u.user = :user")
  Optional<UserInfo> findByUser(@Param("user") User user);
}
