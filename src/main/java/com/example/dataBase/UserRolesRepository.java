package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.UserRoles;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Integer>{

}
