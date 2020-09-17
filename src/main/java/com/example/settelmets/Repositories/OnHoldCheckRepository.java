package com.example.settelmets.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.settelmets.Models.Chaque;
import com.example.settelmets.RTGSLink.SettlementReportModel;

@Repository
public interface OnHoldCheckRepository extends JpaRepository<Chaque,Integer>,PagingAndSortingRepository<Chaque,Integer> {

	public Slice<Chaque> findByActiveTrue(Pageable pageable) ;
	public Slice<Chaque> findByActiveFalse(Pageable pageable) ;
	public List<Chaque> findByActiveFalse() ;
	public List<Chaque> findByActiveTrue() ;
	public Chaque findBysequenceNum(int sequenceNum);
	public Slice<Chaque> findByCheckId(int checkId, Pageable pageable);
	public List<Chaque> findBysettlementReportModel(SettlementReportModel srm);
}
