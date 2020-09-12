 package com.example.Vouchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServicesPool;
import com.example.Loans.Loans;
import com.example.SiteConfig.MasterService;


@Service
public class VoucherService {

	@Autowired 
	VouchersRepository voucherRepository ;
	
	@Autowired
	private ServicesPool servicePool ; 
	
	public void addVoucher(Vouchers v)
	{
		voucherRepository.save(v);
	}
	
	public List<Vouchers> GetAllVouchers ()
	{
		return voucherRepository.findAll();
	}
	
	public void deleteVoucher(int id) {
		voucherRepository.deleteById(id);
	}
	
	public Vouchers GetVoucherById(int id)
	{
		List<Vouchers> allVoucher=voucherRepository.findAll();
		if(allVoucher.isEmpty())
		{
			System.out.println("empty Voucher List ");
			return null ;
		}
		for (Vouchers voucher : allVoucher)
		{
			if(voucher.getVoucherID()==id)
				return voucher;
		}
		System.out.println("requested voucher not found ");
		return null;
		
	}

	public void updateVoucher(Vouchers voucher) {
		try {
			if(voucherRepository.findById(voucher.getVoucherID()) != null) {
					voucherRepository.save(voucher); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at voucher Service / Update voucher -- call for null voucher ");
		}
	}
	
	/*public int getLoanVocher(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll();
		int  NumberofloanVoucher =0;
		for(Vouchers voucher : allVoucher )
		{	
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getLoanID() == id)
					NumberofloanVoucher++;	
			}
		}
		return NumberofloanVoucher;
	}
	*/
	
	public List<Vouchers> getVoucherForThisLoan(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll();
		ArrayList <Vouchers> LoanVoucher = new ArrayList<Vouchers>(); 
		for(Vouchers voucher : allVoucher )
		{
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getId()==id)
					LoanVoucher.add(voucher);
			}
		}
		return LoanVoucher;
	}
	
	
	public boolean AllVoucherPaid(int id)
	{
		List <Vouchers> allVoucher = voucherRepository.findAll(); 
		for(Vouchers voucher : allVoucher )
		{
			if(voucher.getLoan() !=null) {
				if(voucher.getLoan().getId()==id)
					if(!voucher.getStatus().equalsIgnoreCase("paid"))
					  return false ;
			}
		}
		return true;
	}

	
	public int getVouchersValueForLoan(int loanId ) {
		List<Vouchers> vouchersList  = this.getVoucherForThisLoan(loanId);
		int total = 0 ;
		if(vouchersList.size() != 0 ) {
			for(Vouchers voucher : vouchersList ) {
				total += Integer.valueOf(voucher.getTotal());
			}
		}
		return total ; 
	}
	
	
	
	//conflict resolving methods 
	
	
	//remove the loan and its vouchers 
	public void removeLoanPermenant(int loanId) {
		List<Vouchers> loanVouchers = this.getVoucherForThisLoan(loanId);
		for(Vouchers voucher : loanVouchers) {
			this.deleteVoucher(voucher.getVoucherID());
		}
		servicePool.getLoansService().DeleteLoan(loanId);
	}
	
	//remove the loan vouchers only 
	public void removeVouchersOnly(int loanId) {
		List<Vouchers> loanVouchers = this.getVoucherForThisLoan(loanId);
		for(Vouchers voucher : loanVouchers) {
			this.deleteVoucher(voucher.getVoucherID());
		}
	}
	
	/*
	//create new voucher Objects and fill them with zero values and link them to the loan object 
	public void fillVouchersZeroValues(int loanId,int numOfRemainingVouchers) {
		for(int i  = 0 ; i < numOfRemainingVouchers ; i++) {
		Vouchers voucher = new Vouchers();
		voucher.setLoan(loansService.getOneByID(loanId));
		voucher.initWithZeroValues(); 
		this.addVoucher(voucher);
		}
	}
	*/
	
	public boolean checkLoanVouchersTotalValue(Loans loan , int totalVouchersValue) {
		if(Integer.valueOf(loan.getNetAmmount()) < totalVouchersValue )
			return false ; 
		return true ; 
	}
	
	//end of conflict resolving 
	
	
	//data validation methods 
	//data check ok/error switch method
	public String validateVoucherInfo(Vouchers voucher) {
		if(!checkDateValidation(voucher.getVoucherDate())){
			return "voucher date should be bigger than the current date";
		}
		if(voucher.getClient() == null ) {
			return "client is empty ";
		}
		if(voucher.getFundingRatio().equalsIgnoreCase("") || voucher.getFundingRatio().equalsIgnoreCase(" ")) {
			return "funding ratio is not valid";
		}
		if(!checkFloatOrDoubleData(voucher.getFundingRatio())) {
			return "funcing ratio is not valid";
		}
		if(voucher.getLoan() == null ) {
			return "loan not found";
		}
		if(!checkIntegerData(voucher.getNetAmmount())) {
			return "net amount should be a positive integer value";
		}
		if(!checkIntegerData(voucher.getTotal())){
			return "total ammount should be a positive integer value";
		} 
		return "ok";
	}

	//this method checks for the positive float or double values // the point character is allowed once only (example : 1.5)
	public boolean checkFloatOrDoubleData(String data) {
		int pointCounter = 0 ; 
		for(char c : data.toCharArray()) {
			if(!Character.isDigit(c)) {
				if(c != '.') {
				return false ;
				} 
			}
			if( c == '.' && pointCounter !=  0) {
				return false ; 
			}
			if(c == '.' && pointCounter ==  0) {
				pointCounter++ ; 
			}
		}
		return true ; 
	}
	
	//only allow positive integer values
	public boolean checkIntegerData(String data) {
		for(char c : data.toCharArray()) {
			if(!Character.isDigit(c)) {
				return false ; 
			}
		}
		return true ; 
	}
	
	//check if the entered date is after the current date 
	public boolean checkDateValidation(String date) {
		LocalDate CurrentDate = MasterService.getCurrDate();
		LocalDate voucherDate = LocalDate.parse(date);
		if(CurrentDate.isAfter(voucherDate)) {
			return false ; 
		}
		return true ; 
	}
	
	//data validation end 
	
}
