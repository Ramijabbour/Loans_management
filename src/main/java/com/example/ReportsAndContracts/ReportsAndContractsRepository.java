package com.example.ReportsAndContracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository

public interface ReportsAndContractsRepository extends JpaRepository<ReportsAndContracts,Integer>,PagingAndSortingRepository<ReportsAndContracts,Integer> {

}