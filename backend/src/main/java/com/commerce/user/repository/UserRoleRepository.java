package com.commerce.user.repository;

import com.commerce.datamodel.UserRole;
import com.commerce.id.UserRoleId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository
  extends JpaRepository<UserRole, UserRoleId>
{
  
  @Query("SELECT ur FROM UserRole ur WHERE ur.userId = :userId AND ur.roleId = :roleId")
  Optional<UserRole> getUserRoleById(@Param("userId") int userId, @Param("roleId") int roleId);
  
  Optional<UserRole> findByUserIdAndRoleId(int userId, int roleId);
  
  @Query("DELETE FROM UserRole ur WHERE ur.userId = :userId AND ur.roleId = :roleId")
  void deleteByIdUserIdAndIdRoleId(@Param("userId") int userId, @Param("roleId") int roleId);
}