package com.example.WareHouseRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Banks.Banks;
import com.example.WareHouseModel.Client_Dim;

public interface ClientDimRepository extends JpaRepository<Client_Dim,Integer>{

	@Query("select Client_Dim from Client_Dim client where client.id = :#{#id}")
    public Client_Dim findByid(int id);
	 
}
