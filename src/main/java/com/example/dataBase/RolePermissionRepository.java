package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.RolePermission;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission,Integer>{

}
