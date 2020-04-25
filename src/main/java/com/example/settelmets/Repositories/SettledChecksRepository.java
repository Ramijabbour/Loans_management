package com.example.settelmets.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.settelmets.Models.SettledChaque;

public interface SettledChecksRepository extends JpaRepository<SettledChaque,Integer> {

}
