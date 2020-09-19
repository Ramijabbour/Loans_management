package com.example.WareHouseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankBranches.BrancheService;
import com.example.BankBranches.Branches;
import com.example.Clients.ClientService;
import com.example.Clients.Clients;
import com.example.FinanceType.FinanceType;
import com.example.FinanceType.FinanceTypeService;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import com.example.LoansType.LoansType;
import com.example.LoansType.LoansTypeService;
import com.example.WareHouseRepository.BranchBankDimRepository;
import com.example.WareHouseRepository.ClientDimRepository;
import com.example.WareHouseRepository.ClientLoanRepository;
import com.example.WareHouseRepository.FactRepository;
import com.example.WareHouseRepository.FinanceTypeDimRepository;
import com.example.WareHouseRepository.LoansTypeDimRepository;
import com.example.WareHouseRepository.TimeRepository;
import com.example.WareHouseRepository.UserDimRepository;
import com.example.WareHouseService.FactTableService;
import com.example.security.user.User;
import com.example.security.user.UserService;

@RestController
public class insert {

	@Autowired 
	ClientService clientsService ; 
	@Autowired
	UserService userService ; 
	@Autowired
	FinanceTypeService financeTypeService ; 
	@Autowired
	LoansTypeService loanTypeService ; 
	@Autowired 
	BrancheService brancheService; 
	@Autowired 
	LoanService loanService; 
	
	
	@Autowired
	ClientDimRepository ClientDimRepo;
	@Autowired
	UserDimRepository userDimRepo;
	@Autowired
	FinanceTypeDimRepository financeTypeDimRepo;
	@Autowired
	LoansTypeDimRepository loanTypeDimRepo;
	@Autowired
	BranchBankDimRepository brancheBankRepo;
	@Autowired 
	TimeRepository timeRepo;
	@Autowired
	ClientLoanRepository clientLoanRepo; 
	
	@Autowired
	FactRepository factRepo;
	
	@Autowired 
	FactTableService factService  ; 
	
	@RequestMapping(method = RequestMethod.GET , value = "/insert")
	public void insertIntoDimensions() throws java.text.ParseException
	{/*
		List<Clients> allClients = clientsService.GetClientsByType("شخص");
		
		for(Clients client : allClients)
		{
			Client_Dim c = new Client_Dim(client.getId(),client.getClientName(),client.getAddress(),client.getGender(),client.getMarried(),client.getNumberOFChilderen(),client.getAge(),client.getIncome());
			ClientDimRepo.save(c);
		}
		
		List<User> allUser =userService.getAllUsers(); 
		
		for(User user : allUser)
		{
			User_Dim u = new User_Dim(user.getId(),user.getEmail(),user.getPassword(),user.getUsername(),user.getGender(),user.getUserPermissions(),user.getUserRoles(),user.isActive());
			userDimRepo.save(u);
		}
		
		List<LoansType> allType= loanTypeService.getAllType();
		for(LoansType loan : allType)
		{
			LoansType_Dim l = new LoansType_Dim(loan.getLoanTypeID(),loan.getTypeName());
			loanTypeDimRepo.save(l);
		}
		
		List<FinanceType> allFinanceType= financeTypeService.getAllFinanceType();
		for(FinanceType finance : allFinanceType)
		{
			FinanceType_Dim fTD= new FinanceType_Dim(finance.getFinanceTypeID(),finance.getTypeName(),finance.getFundintRate());
			financeTypeDimRepo.save(fTD);
		}
		
		List<Branches> allBranche=brancheService.getAllBrancheNoPage();
		for( Branches branche : allBranche)
		{
			Branch_Bank_Dim b = new Branch_Bank_Dim(branche.getId(),branche.getBranchName(),branche.getBrancheCode(),branche.getBank().getBankName(),branche.getBank().getFinancialAllocations());
			brancheBankRepo.save(b);
		}
		List<Loans>allLoans= loanService.FindAllLoans();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    ArrayList<Time_Dim> alltime = new ArrayList<Time_Dim>();
		for(Loans loan :allLoans)
		{
			try {
				Date firstDate = sdf.parse(loan.getLoanDate());
				System.out.println("correct Date " +loan.getLoanDate());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(firstDate);
				int day , month,year; 
				year= calendar.get(Calendar.YEAR);
				month=calendar.get(Calendar.MONTH)+1;
				day=calendar.get(Calendar.DAY_OF_MONTH);
				
				Time_Dim time = new Time_Dim(loan.getLoanDate(),day,month,year);
				if(!alltime.contains(time))
					alltime.add(time);
				
					//timeRepo.save(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	
		for(Time_Dim t : alltime)
		{
			timeRepo.save(t);
		}
		
		
		List<Loans>allLoans= loanService.FindAllLoans();
		for(Loans loan :allLoans)
		{
			String status = loanService.GetLoanStatus(loan.getId());
			List<Time_Dim> allTime = timeRepo.findAll();
			Time_Dim time =null;
			for(Time_Dim t : allTime)
				if(t.getDate().equalsIgnoreCase(loan.getLoanDate()))
					time=t;
			
			if(loan.getClient().getClientType().equalsIgnoreCase("شخص"))	{
				Branch_Bank_Dim branch_Bank = brancheBankRepo.findByid(loan.getBranche().getId());
				LoansType_Dim loantype = loanTypeDimRepo.findByid(loan.getLoanType().getLoanTypeID());
				FinanceType_Dim financeType = financeTypeDimRepo.findByid(loan.getFinanceType().getFinanceTypeID());
				//User_Dim user  = userDimRepo.findByid(loan.getUser().getId());
				Client_Dim client =ClientDimRepo.findByid(loan.getClient().getId());
				Fact_Table fact = new Fact_Table(loan.getTotalAmmount(),loan.getNetAmmount(),status,client,branch_Bank,null,loantype,financeType,time);
				factRepo.save(fact);
			}	
		}
		System.out.println("Done !!");
		
			
		List<Fact_Table> allloans= factService.filterFact();
		
		for(Fact_Table f : allloans)
		{
			String result = null;
			if(f.getStatus().equalsIgnoreCase("مغلقة"))
				result="yes";
			else 
				result="no";
			
			int age = Integer.parseInt(f.getClient().getAge());
			ClientLoan c = new ClientLoan(f.TotalAmmount,f.NetAmmount,f.getStatus(),f.getClient().getAddress(),f.getClient().getGender(),f.getClient().getMarried(),f.getClient().getNumberOFChilderen(),age,f.getLoanType().getTypeName(),f.getFinanceType().getTypeName(),f.getClient().getIncome(),result);
			clientLoanRepo.save(c);
		}
		*/
		
		
		
	}

}
