package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.aspect.Exceptions;
import com.example.dataBase.RolesRepository;
import com.example.models.Roles;

@Service
public class RolesService {

	
	@Autowired
	RolesRepository rolesRepository ; 
	
	
	public void addRole(Roles role ) {
		if(this.rolesRepository.findAll().contains(role)) {
			throw new Exceptions(-405,"role already exist in the System");
		}
		this.rolesRepository.save(role); 
	}
	
	public void updateRole(Roles role ) {
		if (this.rolesRepository.findById(role.getRoleID()) == null ) {
			throw new Exceptions(-404,"item not found in the System");
		}else 
			{
				this.rolesRepository.save(role);
			} 
	}
	
	public void deleteRole(Roles role ) {
		this.rolesRepository.delete(role);
	}
	
	
}
