package com.example.CloseLoans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface CloseLoansRepository extends JpaRepository<CloseLoans,Integer>{

}
