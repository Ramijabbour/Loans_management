package com.example.OpenLoans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface OpenLoansRepository extends JpaRepository<OpenLoans,Integer> {


    @Query("select count(*) from OpenLoans")
    public int getOpenLoansCount();
	
}
