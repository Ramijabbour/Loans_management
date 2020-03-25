package com.example.MQ;

import com.example.LoansType.LoansType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepsitory extends JpaRepository<Money,Integer> {
}
