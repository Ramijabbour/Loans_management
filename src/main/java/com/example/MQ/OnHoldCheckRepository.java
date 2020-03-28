package com.example.MQ;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnHoldCheckRepository extends JpaRepository<Chaque,Integer> {

	public List<Chaque> findByActiveTrue() ;
	public List<Chaque> findByActiveFalse() ;
	
}
