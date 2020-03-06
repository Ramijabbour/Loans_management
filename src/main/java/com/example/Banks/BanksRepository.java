package com.example.Banks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BanksRepository extends JpaRepository<Banks,Integer>{



}
