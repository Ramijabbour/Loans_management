package com.example.Notifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification,Integer>,PagingAndSortingRepository<Notification,Integer>{
	
}
