package com.example.Banks;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BanksRepository extends JpaRepository<Banks,Integer>, PagingAndSortingRepository<Banks,Integer> {

    @Query("select banks from Banks banks where banks.bankName = :#{#bankName}")
    public Slice<Banks> findByBankName(String bankName, Pageable pageable);


}
