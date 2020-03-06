package com.example.FinanceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FinanceTypeRepository extends JpaRepository<FinanceType,Integer>{

}
