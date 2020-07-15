package com.example.WareHouseRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WareHouseModel.Fact_Table;

public interface FactRepository extends JpaRepository<Fact_Table,Integer>{

}
