package com.example.settelmets.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.settelmets.Models.Chaque;

@Repository
public interface OnHoldCheckRepository extends JpaRepository<Chaque,Integer>,PagingAndSortingRepository<Chaque,Integer> {

	public List<Chaque> findByActiveTrue() ;
	public List<Chaque> findByActiveFalse() ;
	public Slice<Chaque> findByCheckId(int checkId, Pageable pageable);
}
