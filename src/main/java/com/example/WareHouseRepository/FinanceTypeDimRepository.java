package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WareHouseModel.Client_Dim;
import com.example.WareHouseModel.FinanceType_Dim;

public interface FinanceTypeDimRepository extends JpaRepository<FinanceType_Dim,Integer>{

	@Query("select FinanceType_Dim from FinanceType_Dim FinanceType_Dim where FinanceType_Dim.FinanceTypeID = :#{#id}")
    public FinanceType_Dim findByid(int id);
	
}
