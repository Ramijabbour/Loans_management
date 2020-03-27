package com.example.dataBase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FinanceType.FinanceType;
import com.example.FinanceType.FinanceTypeRepository;
import com.example.LoansType.LoansType;
import com.example.LoansType.LoansTypeRepository;
import com.example.security.roles.Roles;
import com.example.security.roles.RolesRepository;
import com.example.security.user.User;
import com.example.security.user.UserRepository;


@Service
public class DbInit implements CommandLineRunner{

	private UserRepository userRepository ; 
	private PasswordEncoder passwordEncoder ; 
	private RolesRepository rolesRepo; 
	private LoansTypeRepository TypeRepo;
	private FinanceTypeRepository financeRepo;
	
	public DbInit(UserRepository userRepo ,PasswordEncoder passwordEncoder,RolesRepository rolesRepo) {
		this.userRepository = userRepo;
		this.passwordEncoder = passwordEncoder ; 
		
		this.rolesRepo = rolesRepo ; 
	}
	/*
	public DbInit() {}
	public DbInit(LoansTypeRepository loantype ,FinanceTypeRepository financeType)
	{
		this.TypeRepo=loantype;
		this.financeRepo=financeType;
	}*/

	@Override
	public void run(String... args) throws Exception {
	
		//User admin = new User("admin@email.com",passwordEncoder.encode("admin123"),"admin","male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
		//this.userRepository.save(admin);
		//User tester = new User("tester@email.com",passwordEncoder.encode("tester123"),"tester","male","getAllUsers","TESTER",true);
		//this.userRepository.save(tester);
		//Roles testRole = new Roles("TEST","test1,test2,test3");
		//this.rolesRepo.save(testRole);
		/*
		LoansType l = new LoansType("مرخص");
		this.TypeRepo.save(l);
		
		FinanceType f=new FinanceType("مواسم استراتيجية");
		this.financeRepo.save(f);
		*/
	}


}
