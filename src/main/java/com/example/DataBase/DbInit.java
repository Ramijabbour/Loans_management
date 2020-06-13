package com.example.DataBase;

import java.time.LocalDate;
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
import com.example.Clients.ClientsRepository;
import com.example.FinanceType.FinanceType;
import com.example.FinanceType.FinanceTypeRepository;
import com.example.Loans.Loans;
import com.example.Loans.LoansRepository;
import com.example.LoansType.LoansType;
import com.example.LoansType.LoansTypeRepository;
import com.example.OpenLoans.OpenLoans;
import com.example.OpenLoans.OpenLoansRepository;
import com.example.Vouchers.Vouchers;
import com.example.Vouchers.VouchersRepository;
import com.example.security.UserRoles.UserRole;
import com.example.security.UserRoles.UserRoleRepository;
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
	private ClientsRepository clientsRepo ; 
	private UserRoleRepository userRoleRepository ;
	private VouchersRepository vouchersRepo ; 
	
	public DbInit(OnHoldCheckRepository onHoldRepositpry ,UserRepository userRepository,PasswordEncoder passwordEncoder
				,LoansTypeRepository LtypeRepo,FinanceTypeRepository financeRep,BanksRepository banksRepo
				,AllocationsRepository allocationsRepo,LoansRepository loansRepo,BrancheRepository branchRepo
				,RolesRepository rolesRepo ,  OpenLoansRepository openLoansRepo, ClientsRepository clientsRepo
				,UserRoleRepository userRoleRepository,VouchersRepository vouchersRepo) {
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
		this.clientsRepo = clientsRepo ;  
		this.userRoleRepository = userRoleRepository;
		this.vouchersRepo = vouchersRepo ; 
	}

	@Override
	public void run(String... args) throws Exception {
		//injectUsers_Roles_ToDB();
		//inject_Banks_Allocations_Finance_Branches_LoansTypes_Loans();
		//injectChecksToDB();
		//System.out.println("injection Done !! "); 
	}
	

	public void inject_Banks_Allocations_Finance_Branches_LoansTypes_Loans() {
		//insert clients // 
		List<Clients> allClients = new ArrayList<Clients>(); 
		boolean switchG = false ; 
		for(int i = 0 ; i < 60 ; i ++) {
			Clients client = new Clients("المتعامل"+i,"","003135658"+i,"client"+i+"mail@gmail.com","093737101"+i,
					"دمشق"," ");
			if(i%2 == 0 ) {
				client.setClientType("شخص");
				if(switchG) {
					client.setGender("ذكر");
					switchG = false ; 
				}else {
					client.setGender("أنثى");
					switchG = true ; 
				}
			}else {
				client.setClientType("مؤسسة");	
			}
			this.clientsRepo.save(client);
			allClients.add(client);
		}
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
			String date = yearAsString +"-01-01";
			allocation.setAllocationDate(date); 
			year++ ; 
			this.allocationsRepo.save(allocation);
		}
		}
		//insert branch to set the loans to 
		
			
		int financeType = 0 ; 
		int loanType = 0 ;
		int branchesCounter = 0 ; 
		int clientsCounter = 0 ; 
		for(int j = 0 ; j < 5 ; j++) {
			int year = 1980 ;
			for(int i = 1 ; i < 40 ; i ++) {
			int totalLoanValue = 50000+ ThreadLocalRandom.current().nextInt(10000, 9000000 + 1);; 
			int intrestRate = ThreadLocalRandom.current().nextInt(2,20);
			Loans loan = new Loans("السلفة"+i,"الطرف الأول","الطرف الثاني",String.valueOf(i),
				String.valueOf(intrestRate),String.valueOf(intrestRate-1),"ر.ت"+i,String.valueOf(totalLoanValue),
				String.valueOf(totalLoanValue),String.valueOf(totalLoanValue),String.valueOf(totalLoanValue), "3",
				"3","شراء بضاعة",allClients.get(clientsCounter), branchList.get(branchesCounter), null, loansTypes.get(loanType),
			fTypes.get(financeType));
		String yearAsString = String.valueOf(year);
		int month = ThreadLocalRandom.current().nextInt(1,13);
		String monthAsString = String.valueOf(month);
		if(month != 10) {
			if( month != 11)
				if( month != 12)
			monthAsString = "0"+monthAsString;
		}
		int day = ThreadLocalRandom.current().nextInt(1,28);
		String sDay = String.valueOf(day);
		if(sDay.length() == 1 ) {
			sDay = "0" + sDay; 
		}
		String date = yearAsString +"-"+monthAsString+"-"+sDay;
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
		injectVouchersToLoan(loan,allClients.get(clientsCounter));
		if(clientsCounter < allClients.size()-1) {
			clientsCounter ++ ; 
		}else {
			clientsCounter = 0 ; 
		}
		
			}
		}
		
	}

	public void injectUsers_Roles_ToDB() {
		boolean adminFound = false , superFound = false ,statsFound = false , allStatsFound = false ,loansFound = false  ; 
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
			if(role.getRoleName().equalsIgnoreCase("LOANS")) {
				loansFound = true ; 
			}
		}
		Roles adminRole = new Roles() ;
		Roles superRole = new Roles() ;
		Roles analyticsRole = new Roles() ;
		Roles allAnalyticsRole = new Roles() ;
		Roles loansRole = new Roles();
		if(!adminFound) {
			adminRole.setRoleName("ADMIN");
			this.rolesRepo.save(adminRole);
		}
		if(!superFound) {
			superRole.setRoleName("SUPER");
			this.rolesRepo.save(superRole); 
		}
		if(!statsFound) {
			analyticsRole.setRoleName("ANALYTICS");
			this.rolesRepo.save(analyticsRole); 
		}
		if(!allStatsFound) {
			allAnalyticsRole.setRoleName("ALLANALYTICS");
			this.rolesRepo.save(allAnalyticsRole); 
		}
		if(!loansFound) {
			loansRole.setRoleName("LOANS");
			this.rolesRepo.save(loansRole);
		}
		
		
		User superUser = new User("SuperAccount@Gmail.com",passwordEncoder.encode("ADMIN123qwe"),"superadmin","male","","SUPER",true);
		this.userRepository.save(superUser);
		UserRole ur = new UserRole(superUser,superRole);
		this.userRoleRepository.save(ur);
		
		User adminUser = new User("AdminAccount@Gmail.com",passwordEncoder.encode("ADMIN123qwe"),"admin","male","","ADMIN",true); 
		this.userRepository.save(adminUser);
		UserRole ur2 = new UserRole(adminUser,adminRole);
		this.userRoleRepository.save(ur2);
		
		User loansUser = new User("LoansAccount@Gmail.com",passwordEncoder.encode("ADMIN123qwe"),"loansuser","male","","LOANS",true);
		this.userRepository.save(loansUser);
		UserRole ur3 = new UserRole(loansUser,loansRole);
		this.userRoleRepository.save(ur3);
		
		User analyticsUser = new User("AnalyticsAccount@Gmail.com",passwordEncoder.encode("ADMIN123qwe"),"statsuser","male","","ANALYTICS",true); 
		this.userRepository.save(analyticsUser);
		UserRole ur4 = new UserRole(analyticsUser,analyticsRole);
		this.userRoleRepository.save(ur4);
		
		User allAnalyticsUser = new User("AllAnalyticsAccount@Gmail.com",passwordEncoder.encode("ADMIN123qwe"),"allstatsuser","male","","ALLANALYTICS",true); 
		this.userRepository.save(allAnalyticsUser);
		UserRole ur5 = new UserRole(allAnalyticsUser,allAnalyticsRole);
		this.userRoleRepository.save(ur5);
		
		
		/*for(int i = 0 ; i < 100 ; i ++) {
			User admin = new User("user@email.com"+String.valueOf(i),passwordEncoder.encode("user123"),"user"+String.valueOf(i),"male","","USER",true);
			this.userRepository.save(admin);
			}
		for(int i = 101 ; i < 110 ; i ++) {
			//System.out.println(i);
			User admin = new User("admin@email.com"+String.valueOf(i),passwordEncoder.encode("admin123"),"admin"+String.valueOf(i),"male","ACCESS_TEST1,ACCESS_TEST2","ADMIN",true);
			this.userRepository.save(admin);
			}*/
	}
	
	/*
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

	*/
	
	public void injectVouchersToLoan(Loans loan , Clients client) {
		int numOfVouchers = Integer.valueOf(loan.getNumberOfVoucher());
		for(int i = 0 ; i < numOfVouchers ; i ++ ) {
			Vouchers voucher = new Vouchers() ; 
			voucher.setLoan(loan);
			voucher.setClient(client);
			voucher.setUser(null);
			int ammount = Integer.valueOf(loan.getTotalAmmount())/numOfVouchers ; 
			voucher.setTotal(String.valueOf(ammount));
			voucher.setVoucherAmmount(String.valueOf(ammount));
			voucher.setNetAmmount(String.valueOf(ammount));
			voucher.setStatus("open");
			voucher.setFundingRatio(loan.getInterestRate());
			String year = getYearFromStringDate(loan.getLoanDate());
			String voDate = generateDateForVoucher(Integer.valueOf(year)) ; 
			voucher.setVoucherDate(voDate);
			this.vouchersRepo.save(voucher);
		}
		
	}
	
	public String generateDateForVoucher(int year) {
		int randomYear = ThreadLocalRandom.current().nextInt(year+1,2040);
		String yearAsString = String.valueOf(randomYear);
		int month = ThreadLocalRandom.current().nextInt(1,13);
		String monthAsString = String.valueOf(month);
		if(month != 10) {
			if( month != 11)
				if( month != 12)
			monthAsString = "0"+monthAsString;
		}
		int day = ThreadLocalRandom.current().nextInt(1,28);
		String sDay = String.valueOf(day);
		if(sDay.length() == 1 ) {
			sDay = "0" + sDay; 
		}
		String date = yearAsString +"-"+monthAsString+"-"+sDay;
		System.out.println("loan date "+date);
		return date;
	}
	
	public String getYearFromStringDate(String date) {
		LocalDate desiredDate = LocalDate.parse(date);
		int year = desiredDate.getYear(); 
		return String.valueOf(year) ; 
	}
	
}
