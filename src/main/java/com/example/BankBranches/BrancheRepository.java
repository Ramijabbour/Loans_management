package com.example.BankBranches;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrancheRepository  extends JpaRepository <Branches,Integer> {

}