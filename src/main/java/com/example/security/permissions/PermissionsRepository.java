package com.example.security.permissions;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions,Integer>,PagingAndSortingRepository<Permissions,Integer> {

	@Query("select permissions from Permissions permissions where permissions.PermissionName = :#{#permissionName}")
	public Slice<Permissions> findByPermissionName(String permissionName, Pageable pageable);
	
}
