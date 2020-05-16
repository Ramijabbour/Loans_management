package com.example.Clients;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientsRepository extends JpaRepository<Clients,Integer>,PagingAndSortingRepository<Clients,Integer>{

    public Slice<Clients> findByClientName(String clientName, Pageable pageable);
}
