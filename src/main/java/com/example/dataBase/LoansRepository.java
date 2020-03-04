package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Loans;

@Repository
public interface LoansRepository extends JpaRepository<Loans,Integer>{

}
