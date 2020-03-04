package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Integer> {

}
