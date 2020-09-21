package com.example.settelmets.RTGSLink;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RTGSUserRepository extends JpaRepository<RTGSUser,Integer> {
	
	public List<RTGSUser> findBybankName (String bankName);
	
	public List<RTGSUser> findBySentFalse();
	
}
