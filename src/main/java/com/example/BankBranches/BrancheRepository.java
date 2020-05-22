package com.example.BankBranches;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrancheRepository  extends JpaRepository <Branches,Integer> ,PagingAndSortingRepository<Branches,Integer>{
    @Query("select branches from Branches branches where branches.brancheCode = :#{#brancheCode}")
    public Slice<Branches> findByBrancheCode(String brancheCode, Pageable pageable);

    
    @Query("select count(*) from Branches")
    public int getBranchesCount();
    
}
