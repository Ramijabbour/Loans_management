package com.example.Allocations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AllocationsRepository extends JpaRepository<Allocations, Integer> {

}
