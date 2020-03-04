package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.OpenLoans;

@Repository
public interface OpenLoansRepository extends JpaRepository<OpenLoans,Integer> {

}
