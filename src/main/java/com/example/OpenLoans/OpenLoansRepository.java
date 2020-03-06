package com.example.OpenLoans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OpenLoansRepository extends JpaRepository<OpenLoans,Integer> {

}
