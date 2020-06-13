package com.example.settelmets.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.settelmets.Models.SettledChaque;

public interface SettledChecksRepository extends JpaRepository<SettledChaque,Integer>,PagingAndSortingRepository<SettledChaque,Integer> {


}
