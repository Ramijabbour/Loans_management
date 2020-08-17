package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WareHouseModel.LoansType_Dim;
import com.example.WareHouseModel.User_Dim;

public interface UserDimRepository extends JpaRepository<User_Dim,Integer>{

	
	@Query("select User_Dim from User_Dim User_Dim where User_Dim.id = :#{#id}")
    public User_Dim findByid(int id);
	
}
