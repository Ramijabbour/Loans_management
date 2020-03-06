package com.example.Allocations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Allocations;

@Repository
public interface AllocationsRepository extends JpaRepository<Allocations, Integer> {

}
