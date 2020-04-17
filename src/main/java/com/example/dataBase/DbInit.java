package com.example.dataBase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FinanceType.FinanceType;
import com.example.FinanceType.FinanceTypeRepository;
import com.example.LoansType.LoansType;
import com.example.LoansType.LoansTypeRepository;
import com.example.MQ.Chaque;
import com.example.MQ.OnHoldCheckRepository;
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
	
	private OnHoldCheckRepository onHoldRepo ; 
	
	public DbInit(OnHoldCheckRepository onHoldRepositpry ,UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.onHoldRepo = onHoldRepositpry ; 
		this.userRepository = userRepository ; 
		this.passwordEncoder = passwordEncoder ; 
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
		/*Chaque check1 = new Chaque(1,"bank1",1,"bank2",2,1000);
		this.onHoldRepo.save(check1);
		Chaque check6 = new Chaque(6,"bank3",3,"bank4",4,100);
		this.onHoldRepo.save(check6);
		Chaque check3 = new Chaque(3,"bank2",2,"bank1",1,300);
		this.onHoldRepo.save(check3);
		
		Chaque check4 = new Chaque(4,"bank2",2,"bank3",3,5000);
		this.onHoldRepo.save(check4);
		Chaque check11 = new Chaque(11,"bank5",5,"bank3",3,900);
		this.onHoldRepo.save(check11);
		Chaque check2 = new Chaque(2,"bank1",1,"bank3",3,2000);
		this.onHoldRepo.save(check2);
		Chaque check5 = new Chaque(5,"bank3",3,"bank1",1,300);
		this.onHoldRepo.save(check5);

		Chaque check7 = new Chaque(7,"bank3",3,"bank5",5,200);
		this.onHoldRepo.save(check7);
		Chaque check8 = new Chaque(8,"bank4",4,"bank1",1,400);
		this.onHoldRepo.save(check8);
		Chaque check12 = new Chaque(12,"bank5",5,"bank4",4,600);
		this.onHoldRepo.save(check12);
		Chaque check9 = new Chaque(9,"bank4",4,"bank3",3,200);
		this.onHoldRepo.save(check9);
		Chaque check10 = new Chaque(10,"bank5",5,"bank1",1,300);
		this.onHoldRepo.save(check10);*/
	
		
		
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
