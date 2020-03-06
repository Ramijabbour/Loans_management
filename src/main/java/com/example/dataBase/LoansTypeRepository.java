package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.LoansType;

@Repository
public interface LoansTypeRepository extends JpaRepository<LoansType,Integer> {

}
