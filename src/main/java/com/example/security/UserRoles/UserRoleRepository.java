package com.example.security.UserRoles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UserRoleRepository  extends JpaRepository<UserRole,Integer>{
}
