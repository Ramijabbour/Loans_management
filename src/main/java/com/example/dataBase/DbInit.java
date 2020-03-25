package com.example.dataBase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.roles.Roles;
import com.example.security.roles.RolesRepository;
import com.example.security.user.User;
import com.example.security.user.UserRepository;


@Service
public class DbInit implements CommandLineRunner{

	private UserRepository userRepository ; 
	private PasswordEncoder passwordEncoder ; 
	private RolesRepository rolesRepo; 
	
	public DbInit(UserRepository userRepo ,PasswordEncoder passwordEncoder,RolesRepository rolesRepo) {
		this.userRepository = userRepo;
		this.passwordEncoder = passwordEncoder ; 
		
		this.rolesRepo = rolesRepo ; 
	}

	@Override
	public void run(String... args) throws Exception {
	
		//User admin = new User("admin@email.com",passwordEncoder.encode("admin123"),"admin","male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
		//this.userRepository.save(admin);
		//User tester = new User("tester@email.com",passwordEncoder.encode("tester123"),"tester","male","getAllUsers","TESTER",true);
		//this.userRepository.save(tester);
		//Roles testRole = new Roles("TEST","test1,test2,test3");
		//this.rolesRepo.save(testRole); 
	}


}
