package com.example.OpenLoans;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface OpenLoansRepository extends JpaRepository<OpenLoans,Integer> {


    @Query("select count(*) from OpenLoans")
    public int getOpenLoansCount();

    
    @Query("select openLoans from OpenLoans openLoans order by openLoans.id desc")
    public List<OpenLoans> findAll(); 
    
    
}
