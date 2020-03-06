package com.example.Vouchers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface VouchersRepository extends JpaRepository<Vouchers,Integer>{

}
