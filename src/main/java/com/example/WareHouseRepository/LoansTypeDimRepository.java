package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WareHouseModel.FinanceType_Dim;
import com.example.WareHouseModel.LoansType_Dim;;

public interface LoansTypeDimRepository extends JpaRepository<LoansType_Dim,Integer>{

	@Query("select LoansType_Dim from LoansType_Dim LoansType_Dim where LoansType_Dim.LoanTypeID = :#{#id}")
    public LoansType_Dim findByid(int id);
	
}
