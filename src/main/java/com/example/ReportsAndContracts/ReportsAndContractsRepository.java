package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.ReportsAndContracts;

@Repository
public interface ReportsAndContractsRepository extends JpaRepository<ReportsAndContracts,Integer> {

}
