package com.example.dataBase;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Allocations.Allocations;
import com.example.Allocations.AllocationsRepository;
import com.example.BankBranches.BrancheRepository;
import com.example.BankBranches.Branches;
import com.example.Banks.Banks;
import com.example.Banks.BanksRepository;
import com.example.Clients.Clients;
import com.example.FinanceType.FinanceType;
import com.example.FinanceType.FinanceTypeRepository;
import com.example.Loans.Loans;
import com.example.Loans.LoansRepository;
import com.example.LoansType.LoansType;
import com.example.LoansType.LoansTypeRepository;
import com.example.security.roles.RolesRepository;
import com.example.security.user.User;
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
	private BanksRepository banksRepo ; 
	private AllocationsRepository allocationsRepo ; 
	private OnHoldCheckRepository onHoldRepo ; 
	private LoansRepository loansRepo ; 
	private BrancheRepository branchRepo ; 
	
	public DbInit(OnHoldCheckRepository onHoldRepositpry ,UserRepository userRepository,PasswordEncoder passwordEncoder
				,LoansTypeRepository LtypeRepo,FinanceTypeRepository financeRep,BanksRepository banksRepo
				,AllocationsRepository allocationsRepo,LoansRepository loansRepo,BrancheRepository branchRepo) {
		this.onHoldRepo = onHoldRepositpry ; 
		this.userRepository = userRepository ; 
		this.passwordEncoder = passwordEncoder ;
		this.TypeRepo=LtypeRepo;
		this.financeRepo=financeRep;
		this.banksRepo = banksRepo ; 
		this.allocationsRepo = allocationsRepo ; 
		this.loansRepo = loansRepo ; 
		this.branchRepo = branchRepo ; 
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		Chaque check1 = 
		new Chaque(1,"bank1","bank2","br1","#br1","br2","#br2",100000000,"admin",4,false);
		this.onHoldRepo.save(check1);
		
		Chaque check6 = 
		new Chaque(6,"bank1","bank4","br3","#br3","br4","#br4",10000000000L,"admin",4,false);
		this.onHoldRepo.save(check6);
		
		Chaque check3 = 
		new Chaque(3,"bank2","bank1","br2","#br2","br1","#br1",30000000000L,"admin",4,false);
		this.onHoldRepo.save(check3);
		
		Chaque check4 = 
		new Chaque(4,"bank2","bank3","br2","#br2","br3","#br3",500000000000L,"admin",4,false);
		this.onHoldRepo.save(check4);
		
		Chaque check11 =
		new Chaque(11,"bank5","bank3","br5","#br5","br3","#br3",90000000000L,"admin",4,false);
		this.onHoldRepo.save(check11);
		
		Chaque check2 = 
		new Chaque(2,"bank1","bank3","br1","#br1","br3","#br3",200000000000L,"admin",4,false);
		this.onHoldRepo.save(check2);
		
		Chaque check5 = 
		new Chaque(5,"bank3","bank1","br3","#br3","br1","#br1",30000000000L,"admin",4,false);
		this.onHoldRepo.save(check5);

		Chaque check7 = 
		new Chaque(7,"bank3","bank5","br3","#br3","br5","#br5",20000000000L,"admin",4,false);
		this.onHoldRepo.save(check7);
		
		Chaque check8 = 
		new Chaque(8,"bank4","bank1","br4","#br4","br1","#br1",40000000000L,"admin",4,false);
		this.onHoldRepo.save(check8);
		
		Chaque check12 =
		new Chaque(12,"bank5","bank4","br5","#br5","br4","#br4",60000000000L,"admin",4,false);
		this.onHoldRepo.save(check12);
		
		Chaque check9 =
		new Chaque(9,"bank4","bank3","br4","#br4","br3","#br3",20000000000L,"admin",4,false);
		this.onHoldRepo.save(check9);
		
		Chaque check10 = 
		new Chaque(10,"bank5","bank1","br5","#br5","br1","#br1",30000000000L,"admin",4,false);
		this.onHoldRepo.save(check10);
		*/
	
	
		/*
		for(int i = 0 ; i < 10000 ; i ++) {
		//System.out.println(i);
		User admin = new User("admin@email.com"+String.valueOf(i),passwordEncoder.encode("admin123"),"admin"+String.valueOf(i),"male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
		this.userRepository.save(admin);
		}
		*/
		
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
		/*
		Banks bank = new Banks("bankFromDBInit","0") ; 
		this.banksRepo.save(bank);
		*/
		
		
		
		/*
		Branches branch = new Branches("br44","#br44"); 
		
		for(Banks bank : this.banksRepo.findAll()) {
			if(bank.getBankID() == 4 ) {
				branch.setBank(bank);
			}
		} 
		this.branchRepo.save(branch); 
		
		int year = 1980 ;
		for(int i = 1 ; i < 20 ; i ++) {
			
			int totalLoanValue = 50000+ ThreadLocalRandom.current().nextInt(10000, 9000000 + 1);;  
		Loans loan = new Loans(" "," ", " ", " ",
				" "," ", " ",String.valueOf(totalLoanValue),
				String.valueOf(totalLoanValue), " ", " ", " ",
			" ", " ",null, branch, null, null,
			null);
		String yearAsString = String.valueOf(year);
		String date = yearAsString +"-05-27";
		loan.setLoanDate(date);
		year++;
		this.loansRepo.save(loan); 
		
		}
		*/
		
		//allocation insert 
		//Banks bank = new Banks() ; 
		/*for(Banks tempBank : this.banksRepo.findAll()) {
			if(tempBank.getBankID() == 1 ) {
				bank = tempBank ; 
			}
		}*/
		/*
		for(int dashNum = 0 ; dashNum < 10 ; dashNum++) {
		Banks dashBank = new Banks("dashBank"+dashNum,"0") ; 
		this.banksRepo.save(dashBank);
		
		int year = 1996 ;
		for(int i = 1 ; i <= 24 ; i++) {
			int initialAmount = ThreadLocalRandom.current().nextInt(10000, 9000000 + 1);
			Allocations allocation = new Allocations() ; 
			allocation.setBank(dashBank);
			allocation.setAllocationAmmount(String.valueOf(initialAmount));
			String yearAsString = String.valueOf(year);
			String date = yearAsString +"-05-27";
			allocation.setAllocationDate(date); 
			year++ ; 
			this.allocationsRepo.save(allocation);
		}
		}
		
		*/
	}


}
