package com.example.settelmets.RTGSLink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RTGSUserRepository extends JpaRepository<RTGSUser,Integer> {

	
	
}
