package com.example.settelmets.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.settelmets.Models.ReportsLinkModel;

@Repository
public interface ReportsLinkRepository extends JpaRepository<ReportsLinkModel,Integer> {

	
}
