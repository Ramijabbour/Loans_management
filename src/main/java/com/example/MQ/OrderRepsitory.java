package com.example.MQ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepsitory extends JpaRepository<Chaque,Integer> {
}
