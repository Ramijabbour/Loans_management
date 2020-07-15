package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WareHouseModel.Time_Dim;

public interface TimeRepository extends JpaRepository<Time_Dim,Integer>{

}
