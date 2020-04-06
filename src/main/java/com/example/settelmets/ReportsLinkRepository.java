package com.example.settelmets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsLinkRepository extends JpaRepository<ReportsLinkModel,Integer> {

	
}
