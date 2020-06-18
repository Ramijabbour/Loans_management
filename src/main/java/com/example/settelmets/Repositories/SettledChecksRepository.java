package com.example.settelmets.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.settelmets.Models.SettledChaque;
import com.example.settelmets.RTGSLink.SettlementReportModel;

public interface SettledChecksRepository extends JpaRepository<SettledChaque,Integer>,PagingAndSortingRepository<SettledChaque,Integer> {

	public List<SettledChaque> findBysettlementReportModel(SettlementReportModel srm);
}
