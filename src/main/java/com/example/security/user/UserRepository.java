package com.example.security.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> ,PagingAndSortingRepository<User,Integer>{

	public User findByUsername(String username);
	
	
	@Query("select count(*) from User")
    public int getUsersCount();
}
