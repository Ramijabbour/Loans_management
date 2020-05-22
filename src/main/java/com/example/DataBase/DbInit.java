package com.example.DataBase;

import java.util.ArrayList;
import java.util.List;
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
import com.example.OpenLoans.OpenLoans;
import com.example.OpenLoans.OpenLoansRepository;
import com.example.security.roles.Roles;
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
	private OpenLoansRepository openLoansRepo ; 
	
	public DbInit(OnHoldCheckRepository onHoldRepositpry ,UserRepository userRepository,PasswordEncoder passwordEncoder
				,LoansTypeRepository LtypeRepo,FinanceTypeRepository financeRep,BanksRepository banksRepo
				,AllocationsRepository allocationsRepo,LoansRepository loansRepo,BrancheRepository branchRepo
				,RolesRepository rolesRepo ,  OpenLoansRepository openLoansRepo) {
		this.onHoldRepo = onHoldRepositpry ; 
		this.userRepository = userRepository ; 
		this.passwordEncoder = passwordEncoder ;
		this.TypeRepo=LtypeRepo;
		this.financeRepo=financeRep;
		this.banksRepo = banksRepo ; 
		this.allocationsRepo = allocationsRepo ; 
		this.loansRepo = loansRepo ; 
		this.branchRepo = branchRepo ; 
		this.rolesRepo = rolesRepo ; 
		this.openLoansRepo = openLoansRepo ; 
	}

	@Override
	public void run(String... args) throws Exception {
		//injectUsersToDB();
		//inject_Banks_Allocations_Finance_Branches_LoansTypes_Loans();
		//injectChecksToDB();
		//System.out.println("injection Done !! ");
		//ALLANALYTICS
	
		boolean adminFound = false , superFound = false ,statsFound = false , allStatsFound = false  ; 
		List<Roles> rolesList = this.rolesRepo.findAll() ;
		for(Roles role : rolesList ) {
			if(role.getRoleName().equalsIgnoreCase("ADMIN")){
				adminFound = true ; 
			}
			if(role.getRoleName().equalsIgnoreCase("SUPER")) {
				superFound = true ; 
			}
			if(role.getRoleName().equalsIgnoreCase("ANALYTICS")) {
				statsFound = true ; 
			}
			if(role.getRoleName().equalsIgnoreCase("ALLANALYTICS")) {
				allStatsFound = true ; 
			}		
		}
		Roles role = new Roles() ;
		Roles role1 = new Roles() ;
		Roles role2 = new Roles() ;
		Roles role3 = new Roles() ;
		if(!adminFound) {
			role.setRoleName("ADMIN");
			this.rolesRepo.save(role);
		}
		if(!superFound) {
			role1.setRoleName("SUPER");
			this.rolesRepo.save(role1); 
		}
		if(!statsFound) {
			role2.setRoleName("ANALYTICS");
			this.rolesRepo.save(role2); 
		}
		if(!allStatsFound) {
			role3.setRoleName("ALLANALYTICS");
			this.rolesRepo.save(role3); 
		}
		
	}
	

	public void inject_Banks_Allocations_Finance_Branches_LoansTypes_Loans() {
		//inser finance types // 
		FinanceType f=new FinanceType("مواسم استراتيجية","100");
		
		this.financeRepo.save(f);
		
		FinanceType f1=new FinanceType("طويل الامد","75");
		
		this.financeRepo.save(f1);
		
		FinanceType f2=new FinanceType("قصير الامد","80");
		 
		this.financeRepo.save(f2);
		
		List<FinanceType> fTypes = new ArrayList<FinanceType>() ; 
		fTypes.add(f);fTypes.add(f1);fTypes.add(f2);
		
		
		//insert loans types 
		LoansType l = new LoansType("مرخص");
		this.TypeRepo.save(l);
		LoansType l1 = new LoansType("معفى");
		this.TypeRepo.save(l1);
		List<LoansType> loansTypes = new ArrayList<LoansType>();
		loansTypes.add(l);loansTypes.add(l1);
		
		
		//inserts bank //
		List<Branches> branchList = new ArrayList<Branches>();
		for(int dashNum = 0 ; dashNum < 10 ; dashNum++) {
			Banks dashBank = new Banks("dashBank"+dashNum,"0") ; 
			this.banksRepo.save(dashBank);
			
			Branches branch = new Branches("br"+dashNum,"#br"+dashNum); 
			branch.setBank(dashBank);
			this.branchRepo.save(branch); 
			branchList.add(branch);
		//insertAllocations for this banks 
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
		//insert branch to set the loans to 
		
			
		int financeType = 0 ; 
		int loanType = 0 ;
		int branchesCounter = 0 ; 
		for(int j = 0 ; j < 5 ; j++) {
			int year = 1980 ;
			for(int i = 1 ; i < 40 ; i ++) {
			int totalLoanValue = 50000+ ThreadLocalRandom.current().nextInt(10000, 9000000 + 1);; 
			int intrestRate = ThreadLocalRandom.current().nextInt(2,20);
			Loans loan = new Loans(" "," ", " ", " ",
				String.valueOf(intrestRate)," ", " ",String.valueOf(totalLoanValue),
				String.valueOf(totalLoanValue), " ", " ", " ",
				" ", " ",null, branchList.get(branchesCounter), null, loansTypes.get(loanType),
			fTypes.get(financeType));
		String yearAsString = String.valueOf(year);
		int month = ThreadLocalRandom.current().nextInt(1,13);
		String sMonth = String.valueOf(month);
		if(month != 10) {
			if( month != 11)
				if( month != 12)
			sMonth = "0"+sMonth;
		}
		int day = ThreadLocalRandom.current().nextInt(1,28);
		String sDay = String.valueOf(day);
		if(sDay.length() == 1 ) {
			sDay = "0" + sDay; 
		}
		String date = yearAsString +"-"+sMonth+"-"+sDay;
		System.out.println("loan date "+date);
		if(i %2 == 0 ) {
			loan.setStatus("Confirmed");
		}else {
			loan.setStatus("NotConfirmed");
		}
		loan.setLoanDate(date);
		year++;
		
		if(financeType < 2 ) {
			financeType++;
		}else {
			financeType = 0 ; 
		}
		if(loanType == 0 ) {
			loanType = 1 ;
		}else {
			loanType = 0 ; 
		}
		if(branchesCounter < branchList.size()-1) {
			branchesCounter ++ ;
		}else {
			branchesCounter = 0 ; 
		}
		this.loansRepo.save(loan); 
		OpenLoans openLoan = new OpenLoans();
		openLoan.setLoan(loan);
		this.openLoansRepo.save(openLoan);
		}
		}
		
	}

	public void injectUsersToDB() {
		User superr = new User("SuperAccount@Gmail.com",passwordEncoder.encode("ADMIN123qwe"),"superadmin","male","","SUPER",true);
		this.userRepository.save(superr);
		for(int i = 0 ; i < 100 ; i ++) {
			User admin = new User("user@email.com"+String.valueOf(i),passwordEncoder.encode("user123"),"user"+String.valueOf(i),"male","","USER",true);
			this.userRepository.save(admin);
			}
		for(int i = 101 ; i < 110 ; i ++) {
			//System.out.println(i);
			User admin = new User("admin@email.com"+String.valueOf(i),passwordEncoder.encode("admin123"),"admin"+String.valueOf(i),"male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
			this.userRepository.save(admin);
			}
	}

	public void injectChecksToDB() {
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
	}

}
