package com.example.dataBase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.user.User;
import com.example.security.user.UserRepository;


@Service
public class DbInit implements CommandLineRunner{

	private UserRepository userRepository ; 
	private PasswordEncoder passwordEncoder ; 
	
	public DbInit(UserRepository userRepo ,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepo;
		this.passwordEncoder = passwordEncoder ; 
	}

	@Override
	public void run(String... args) throws Exception {
	
		//User admin = new User("admin@email.com",passwordEncoder.encode("admin123"),"admin","male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
		//this.userRepository.save(admin);
	}


}
