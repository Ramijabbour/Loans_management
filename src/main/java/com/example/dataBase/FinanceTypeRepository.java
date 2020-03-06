package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.FinanceType;

@Repository
public interface FinanceTypeRepository extends JpaRepository<FinanceType,Integer>{

}
