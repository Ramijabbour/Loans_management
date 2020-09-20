package com.example.DataBase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.example.CloseLoans.CloseLoans;
import com.example.CloseLoans.CloseLoansRepository;
import com.example.ReScheduleLoans.ReScheduleLoans;
import com.example.ReScheduleLoans.ReScheduleLoansRepository;
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
	private CloseLoansRepository closeLoanRepository ;
	private ReScheduleLoansRepository reScheduleLoansRepository;


	public DbInit(OnHoldCheckRepository onHoldRepositpry ,UserRepository userRepository,PasswordEncoder passwordEncoder
				,LoansTypeRepository LtypeRepo,FinanceTypeRepository financeRep,BanksRepository banksRepo
				,AllocationsRepository allocationsRepo,LoansRepository loansRepo,BrancheRepository branchRepo
				,RolesRepository rolesRepo ,  OpenLoansRepository openLoansRepo, ClientsRepository clientsRepo
				,UserRoleRepository userRoleRepository,VouchersRepository vouchersRepo,CloseLoansRepository closeLoansRepository,
				  ReScheduleLoansRepository reScheduleLoansRepository) {
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
		this.reScheduleLoansRepository=reScheduleLoansRepository;
		this.closeLoanRepository=closeLoansRepository;
	}

	@Override
	public void run(String... args) throws Exception {
//		injectUsers_Roles_ToDB();
//	inject_Banks_Allocations_Finance_Branches_LoansTypes_Loans();
//		injectChecksToDB();
		System.out.println("injection Done !! ");
	}
	

	public void inject_Banks_Allocations_Finance_Branches_LoansTypes_Loans() {
		//insert clients //
		List<String> name = new ArrayList<String>();
		List<String> age = new ArrayList<String>();
		List<String> Income = new ArrayList<String>();
		Income.add("100000");
		Income.add("150000");
		Income.add("200000");
		Income.add("125000");
		Income.add("175000");
		Income.add("225000");
		Income.add("250000");
		Income.add("275000");
		Income.add("300000");
		Income.add("325000");
		Income.add("350000");
		Income.add("400000");
		Income.add("450000");
		Income.add("500000");
		Income.add("750000");
		Income.add("8250000");
		Income.add("1000000");
		Income.add("1250000");
		Income.add("1500000");
		Income.add("2000000");
		Income.add("3000000");
		List<String> Income2 = new ArrayList<String>();
		List<String> NumberOfChildren = new ArrayList<String>();
		for (int i = 1; i <= 1500; i++) {
			Random r = new Random();
			int a = r.nextInt((60 - 25) + 1) + 25;
			String number = Integer.toString(a);
			age.add(number);

		}
		for (int i = 1; i <= 1500; i++) {
			String number = Integer.toString(i);
			name.add("Client" + number);
		}
		for (int i = 1; i <= 1500; i++) {
			Random r = new Random();
			int a = r.nextInt((60 - 25) + 1) + 25;
			String number = Integer.toString(a);
			age.add(number);

		}
		for (int i = 0; i < age.size(); i++) {
			Random r = new Random();

			while (true) {
				int a = r.nextInt((20 - 0) + 1) + 0;
				int inco = Integer.parseInt(Income.get(a));
				int age2 = Integer.parseInt(age.get(i));
				if (inco > 300000 && age2 > 30 && age2 != 60) {
					String number = Integer.toString(inco);
					Income2.add(number);
					break;
				} else if (inco < 700000 && age2 == 60) {
					String number = Integer.toString(inco);
					Income2.add(number);
					break;
				} else if (inco < 450000 && age2 <= 30) {
					String number = Integer.toString(inco);
					Income2.add(number);
					break;
				}


			}
		}
		for (int i = 0; i < age.size(); i++) {
			Random r = new Random();

			while (true) {
				int a = r.nextInt((7 - 0) + 1) + 0;

				int age2 = Integer.parseInt(age.get(i));
				if (a > 0 && a < 4 && age2 >= 20 && age2 <= 30) {
					String number = Integer.toString(a);
					NumberOfChildren.add(number);
					break;
				} else if (age2 <= 60 && age2 >= 50) {
					String number = Integer.toString(a);
					NumberOfChildren.add(number);
					break;
				} else if (age2 < 50 && age2 > 30 && a < 5) {
					String number = Integer.toString(a);
					NumberOfChildren.add(number);
					break;
				}


			}
		}
		List<Clients> allClients = new ArrayList<Clients>();
		boolean switchG = false;

		for (int i = 0; i < name.size(); i++) {
			Clients client = new Clients(name.get(i), "شخص", "003135658" + i, name.get(i) + "mail@gmail.com", "093737101" + i,
					"", " ", "", NumberOfChildren.get(i), age.get(i), Income2.get(i));
			Random r = new Random();
			int gend = r.nextInt((1 - 0) + 1) + 0;
			if (gend == 0)
				client.gender = "ذكر";
			else
				client.gender = "أنثى";
			int addr = r.nextInt((4 - 0) + 1) + 0;
			if (addr == 0)
				client.address = "دمشق";
			if (addr == 1)
				client.address = "حلب";
			if (addr == 2)
				client.address = "حمص";
			if (addr == 3)
				client.address = "طرطوس";
			if (addr == 4)
				client.address = "الاذقية";
			int marry = r.nextInt((1 - 0) + 1) + 0;
			if (marry == 0)
				client.Married = "أعذب";
			else
				client.Married = "متزوج";

			this.clientsRepo.save(client);
			allClients.add(client);

		}
		//inser finance types //
		FinanceType f = new FinanceType("مواسم استراتيجية", "100");

		this.financeRepo.save(f);

		FinanceType f1 = new FinanceType("طويل الامد", "75");

		this.financeRepo.save(f1);

		FinanceType f2 = new FinanceType("قصير الامد", "80");

		this.financeRepo.save(f2);

		List<FinanceType> fTypes = new ArrayList<FinanceType>();
		fTypes.add(f);
		fTypes.add(f1);
		fTypes.add(f2);


		//insert loans types 
		LoansType l = new LoansType("مرخص");
		this.TypeRepo.save(l);
		LoansType l1 = new LoansType("معفى");
		this.TypeRepo.save(l1);
		List<LoansType> loansTypes = new ArrayList<LoansType>();
		loansTypes.add(l);
		loansTypes.add(l1);


		//inserts bank //
		List<Branches> branchListt = new ArrayList<Branches>();
		List<String> branchList = new ArrayList<String>();
		branchList.add("المحافظة");
		branchList.add("المزة");
		branchList.add("الروضة");
		branchList.add("الميدان");
		branchList.add("التجارة");
		branchList.add("ضاحية الأسد");
		branchList.add("قطنا");
		branchList.add("العدوي");
		branchList.add("أبورمانة");
		List<String> Bank = new ArrayList<String>();
		Bank.add("التوفير");
		Bank.add("الزراعي");
		Bank.add("التسليف الشعبي");
		Bank.add("العقاري");
		Bank.add("الصناعي");
		Bank.add("التجاري");

		for (int dashNum = 0; dashNum < Bank.size(); dashNum++) {
			Banks dashBank = new Banks(Bank.get(dashNum), "0");
			this.banksRepo.save(dashBank);

			for (int j = 0; j < branchList.size(); j++) {
				Branches branch = new Branches(branchList.get(j), "#br" + dashNum);
				branch.setBank(dashBank);
				this.branchRepo.save(branch);
				branchListt.add(branch);
			}

			//insertAllocations for this banks
			int year = 1996;
			for (int i = 1; i <= 24; i++) {
				int initialAmount = ThreadLocalRandom.current().nextInt(10000, 9000000 + 1);
				Allocations allocation = new Allocations();
				allocation.setBank(dashBank);
				allocation.setAllocationAmmount(String.valueOf(initialAmount));
				String yearAsString = String.valueOf(year);
				String date = yearAsString + "-01-01";
				allocation.setAllocationDate(date);
				year++;
				this.allocationsRepo.save(allocation);
			}

		}
		//insert branch to set the loans to
		List<String> LoansAmount = new ArrayList<String>();

		int start = 100000;
		String number3 = Integer.toString(start);
		LoansAmount.add(number3);
		while (true) {
			start += 25000;

			String number2 = Integer.toString(start);
			LoansAmount.add(number2);

			if (start == 10025000)
				break;

		}

		Random r = new Random();


		for (int k = 0; k < 10; k++) {
			int year = 2010;
			for (int i = 1; i < 10; i++) {

				int financeType = r.nextInt(2);
				int BankName = r.nextInt((5 - 0) + 1) + 0;
				int loanType = r.nextInt((1 - 0) + 1) + 0;
				int branchesCounter = r.nextInt((44 - 0) + 1) + 0;
				int clientsCounter = r.nextInt((1499 - 0) + 1) + 0;
				int LoansAmountCounter = r.nextInt((397 - 0) + 1) + 0;
				int totalLoanValue = 50000 + ThreadLocalRandom.current().nextInt(10000, 9000000 + 1);
				;
				int intrestRate = ThreadLocalRandom.current().nextInt(2, 20);
				Loans loan = new Loans("السلفة" + i, branchListt.get(branchesCounter).getBank().bankName, "مصرف سورية المركزي", "1123" + String.valueOf(i),
						String.valueOf(intrestRate), String.valueOf(intrestRate - 1), "ر.ت" + i, LoansAmount.get(LoansAmountCounter),
						LoansAmount.get(LoansAmountCounter) + " ليرة سورية", "", "", "3",
						"3", "", allClients.get(clientsCounter), branchListt.get(branchesCounter), null, loansTypes.get(loanType),
						fTypes.get(financeType));

				double x = Double.parseDouble(fTypes.get(financeType).getFundintRate()) / 100.00;
				double y = Double.parseDouble(LoansAmount.get(LoansAmountCounter));
				double z = x * y;
				loan.setNetAmmount(String.valueOf(z));
				loan.setNetAmmountAsString(String.valueOf(z) + " ليرة سورية");
				String yearAsString = String.valueOf(year);
				int month = r.nextInt((12 - 1) + 1) + 1;
				String monthAsString = String.valueOf(month);
				int day = r.nextInt((28 - 1) + 1) + 1;
				String sDay = String.valueOf(day);
				String date = yearAsString + "-" + monthAsString + "-" + sDay;
//				System.out.println("loan date " + date);
				loan.setStatus("مؤكدة");
				loan.setLoanDate(date);
				int stat = r.nextInt((6 - 1) + 1) + 1;
				this.loansRepo.save(loan);
				if (stat == 1 || stat == 2 || stat == 3 || stat == 4) {
					CloseLoans closeLoans = new CloseLoans();
					closeLoans.setLoan(loan);
					this.closeLoanRepository.save(closeLoans);

				}
				if (stat == 5) {
					OpenLoans openLoan = new OpenLoans();
					openLoan.setLoan(loan);
					this.openLoansRepo.save(openLoan);
				}
				if (stat == 6) {
					ReScheduleLoans reScheduleLoans = new ReScheduleLoans();
					reScheduleLoans.setLoan(loan);
					this.reScheduleLoansRepository.save(reScheduleLoans);
				}

				injectVouchersToLoan(loan, allClients.get(clientsCounter));
			}
			System.out.println("done");
			year++;
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
			//String year = getYearFromStringDate(loan.getLoanDate());
			//String voDate = generateDateForVoucher(Integer.valueOf(year)) ;
			//voucher.setVoucherDate(voDate);
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
