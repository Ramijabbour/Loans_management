package com.example.settelmets.Repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.settelmets.Models.SettledChaque;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface SettledChecksRepository extends JpaRepository<SettledChaque,Integer>,PagingAndSortingRepository<SettledChaque,Integer> {

    public Slice<SettledChaque> findByFirstBankName(String firstName, Pageable pageable);
}
