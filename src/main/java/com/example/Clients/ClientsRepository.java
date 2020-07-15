package com.example.Clients;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientsRepository extends JpaRepository<Clients,Integer>,PagingAndSortingRepository<Clients,Integer>{

    public Slice<Clients> findByClientName(String clientName, Pageable pageable);

    @Query("select count(*) from Clients")
    public int getClientsCount();
    
    @Query("select clientName,Age,Married,address,gender,phone,NumberOFChilderen,income,identity_number from Clients c where c.ClientType =:#{#type}")
    public List<Clients> findClientbyType(String type);
    
}
