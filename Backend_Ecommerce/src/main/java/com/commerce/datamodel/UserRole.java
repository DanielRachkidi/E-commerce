package com.commerce.datamodel;

import com.commerce.id.UserRoleId;
import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "userrole")
@IdClass(UserRoleId.class)
@Getter
@Setter

public class UserRole implements Serializable
{
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name = "id_user")
  private int userId;
  
  @Id
  @Column(name = "id_role", insertable = false, updatable = false)
  private int roleId;
  
  @ManyToOne
  @JoinColumn(name = "id_role", insertable = false, updatable = false)
  private Role role;
  
  @ManyToOne
  @JoinColumn(name = "id_user", insertable = false, updatable = false)
  private User user;
  
  
}