package com.example.dataBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.CloseLoans;


@Repository
public interface CloseLoansRepository extends JpaRepository<CloseLoans,Integer>{

}
