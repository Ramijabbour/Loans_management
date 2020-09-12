package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.WareHouseModel.Client_Dim;

@Repository
public interface ClientDimRepository extends JpaRepository<Client_Dim,Integer>{

	@Query("select Client_Dim from Client_Dim Client_Dim where Client_Dim.id = :#{#id}")
    public Client_Dim findByid(int id);
	 
}
