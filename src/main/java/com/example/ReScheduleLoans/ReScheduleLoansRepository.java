package com.example.ReScheduleLoans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReScheduleLoansRepository extends JpaRepository<ReScheduleLoans,Integer>{

	
	@Query("select count(*) from ReScheduleLoans")
    public int getResLoansCount();

}
