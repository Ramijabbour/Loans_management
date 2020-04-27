package com.example.dataBase;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.FinanceType.FinanceTypeRepository;
import com.example.LoansType.LoansTypeRepository;
import com.example.security.roles.RolesRepository;
import com.example.security.user.UserRepository;
import com.example.settelmets.Models.Chaque;
import com.example.settelmets.Repositories.OnHoldCheckRepository;


@Service
public class DbInit implements CommandLineRunner{

	private UserRepository userRepository ; 
	private PasswordEncoder passwordEncoder ; 
	private RolesRepository rolesRepo; 
	private LoansTypeRepository TypeRepo;
	private FinanceTypeRepository financeRepo;
	
	private OnHoldCheckRepository onHoldRepo ; 
	
	
	public DbInit(OnHoldCheckRepository onHoldRepositpry ,UserRepository userRepository,PasswordEncoder passwordEncoder,LoansTypeRepository LtypeRepo,FinanceTypeRepository financeRep) {
		this.onHoldRepo = onHoldRepositpry ; 
		this.userRepository = userRepository ; 
		this.passwordEncoder = passwordEncoder ;
		this.TypeRepo=LtypeRepo;
		this.financeRepo=financeRep;
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
		/*
		Chaque check1 = 
		new Chaque(1,"bank1","bank2","br1","#br1","br2","#br2",1000,"admin",4,false);
		this.onHoldRepo.save(check1);
		
		Chaque check6 = 
		new Chaque(6,"bank1","bank4","br3","#br3","br4","#br4",100,"admin",4,false);
		this.onHoldRepo.save(check6);
		
		Chaque check3 = 
		new Chaque(3,"bank2","bank1","br2","#br2","br1","#br1",300,"admin",4,false);
		this.onHoldRepo.save(check3);
		
		Chaque check4 = 
		new Chaque(4,"bank2","bank3","br2","#br2","br3","#br3",5000,"admin",4,false);
		this.onHoldRepo.save(check4);
		
		Chaque check11 =
		new Chaque(11,"bank5","bank3","br5","#br5","br3","#br3",900,"admin",4,false);
		this.onHoldRepo.save(check11);
		
		Chaque check2 = 
		new Chaque(2,"bank1","bank3","br1","#br1","br3","#br3",2000,"admin",4,false);
		this.onHoldRepo.save(check2);
		
		Chaque check5 = 
		new Chaque(5,"bank3","bank1","br3","#br3","br1","#br1",300,"admin",4,false);
		this.onHoldRepo.save(check5);

		Chaque check7 = 
		new Chaque(7,"bank3","bank5","br3","#br3","br5","#br5",200,"admin",4,false);
		this.onHoldRepo.save(check7);
		
		Chaque check8 = 
		new Chaque(8,"bank4","bank1","br4","#br4","br1","#br1",400,"admin",4,false);
		this.onHoldRepo.save(check8);
		
		Chaque check12 =
		new Chaque(12,"bank5","bank4","br5","#br5","br4","#br4",600,"admin",4,false);
		this.onHoldRepo.save(check12);
		
		Chaque check9 =
		new Chaque(9,"bank4","bank3","br4","#br4","br3","#br3",200,"admin",4,false);
		this.onHoldRepo.save(check9);
		
		Chaque check10 = 
		new Chaque(10,"bank5","bank1","br5","#br5","br1","#br1",300,"admin",4,false);
		this.onHoldRepo.save(check10);
		*/
		
		//User admin = new User("admin@email.com",passwordEncoder.encode("admin123"),"admin","male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
		//this.userRepository.save(admin);
		//User tester = new User("tester@email.com",passwordEncoder.encode("tester123"),"tester","male","getAllUsers","TESTER",true);
		//this.userRepository.save(tester);
		//Roles testRole = new Roles("TEST","test1,test2,test3");
		//this.rolesRepo.save(testRole);
		/*
		LoansType l = new LoansType("مرخص");
		this.TypeRepo.save(l);
		LoansType l1 = new LoansType("معفى");
		this.TypeRepo.save(l1);
		
		 FinanceType f=new FinanceType("مواسم استراتيجية","100");
		
		this.financeRepo.save(f);
		
		FinanceType f1=new FinanceType("طويل الامد","75");
		
		this.financeRepo.save(f1);
		
		FinanceType f2=new FinanceType("قصير","80");
		 
		this.financeRepo.save(f2);
		*/
		
		
		
	}


}
