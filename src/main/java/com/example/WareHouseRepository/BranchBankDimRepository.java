package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WareHouseModel.Branch_Bank_Dim;
import com.example.WareHouseModel.User_Dim;

public interface BranchBankDimRepository extends JpaRepository<Branch_Bank_Dim,Integer>{

	@Query("select Branch_Bank_Dim from Branch_Bank_Dim Branch_Bank_Dim where Branch_Bank_Dim.id = :#{#id}")
    public Branch_Bank_Dim findByid(int id);
}
