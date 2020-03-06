package com.example.LoansType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoansTypeRepository extends JpaRepository<LoansType,Integer> {

}
