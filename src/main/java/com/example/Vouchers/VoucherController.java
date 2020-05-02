package com.example.Vouchers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.MasterService;
import com.example.Clients.ClientService;
import com.example.Clients.Clients;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;
import com.example.Loans.LoansController;

@RestController
public class VoucherController {

	@Autowired
	private VoucherService voucherService; 
	@Autowired
	private LoanService loanService ;
	@Autowired
	private ClientService clientService ; 
	@Autowired
	private LoansController loansController ; 
	
	// Add New Voucher For This Loan ----------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value = "/Loan/AddVoucher/{id}")
	public ModelAndView ShowAddVoucher(@PathVariable int id ) 
	{
		if(!checkAddVoucher(id))
		{
			ModelAndView mav = new ModelAndView("Vouchers/noVoucher");
			return mav;
		}
		else {
		ModelAndView mav = new ModelAndView("Vouchers/AddVoucher");
		Loans myloan = loanService.getOneByID(id);
		List<Clients> allclient = clientService.GetAllClientsNoPage();
		mav.addObject("voucher", new Vouchers());
		mav.addObject("allclient", allclient);
		mav.addObject("myloan", myloan);
		return mav;
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value="/Loan/AddVoucher/{id}")
	public void addNewVoucher(@ModelAttribute Vouchers voucher,HttpServletResponse response,@PathVariable int id ) throws IOException {
		System.out.println("posted to /Loan/AddVoucher/id ");
		Loans loan=loanService.getOneByID(id);
		voucher.setStatus("Open");
		voucher.setLoan(loan);
		voucherService.addVoucher(voucher);
		response.sendRedirect("/Loans/all/Open?index=0");
	}
	
	// ----------------------------------------------------------------------------
	
	// Get All Voucher For This Loan ----------------------------------------------
	@RequestMapping(method=RequestMethod.GET , value="/Vouchers/all/{id}")
	public ModelAndView allVouchers(@PathVariable int id)
	{
		ModelAndView mav = new ModelAndView("Vouchers/AllVouchers");
		List<Vouchers> allvouchers= voucherService.getVoucherForThisLoan(id);
		mav.addObject("vouchers", allvouchers);
		return mav; 
	}
	
	// Get One Voucher -----------------------------------------------------------
	@RequestMapping(method=RequestMethod.GET , value="/Vouchers/{id}")
	public ModelAndView getVoucher(@PathVariable int id)
	{
		ModelAndView mav = new ModelAndView("Vouchers/OneVoucher");
		Vouchers voucher= voucherService.GetVoucherById(id);
		boolean checkStatus =false ;
		if(voucher.getStatus().equalsIgnoreCase("Open"))
			checkStatus=true;
		mav.addObject("status",checkStatus);
		mav.addObject("voucher", voucher);
		return mav; 
	}
	
	// Delete Vocuher -------------------------------------------------------------
	@RequestMapping(method=RequestMethod.GET , value="/Vouchers/delete/{id}")
	public void DeleteVoucher(@PathVariable int id ,HttpServletResponse response) throws IOException
	{
		int loanid = voucherService.GetVoucherById(id).getLoan().getId();
		voucherService.deleteVoucher(id);
		response.sendRedirect("/Vouchers/all/"+loanid);
		
	}
	// -----------------------------------------------------------------------------
	
	// Update Voucher --------------------------------------------------------------
	@RequestMapping(method=RequestMethod.GET ,value="/Vouchers/edit/{id}")
	public ModelAndView showUpdateVocuher(@PathVariable int id)
	{
		ModelAndView mav = new ModelAndView("Vouchers/UpdateVoucher");
		Vouchers voucher = voucherService.GetVoucherById(id);
		List<Clients> allclient = clientService.GetAllClientsNoPage();
		mav.addObject("voucher", voucher);
		mav.addObject("allclient", allclient);
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST ,value="/Vouchers/update/{id}")
	public void updateVoucher(@PathVariable int id,@Valid Vouchers voucher , HttpServletResponse response) throws IOException
	{
		int loanid=voucherService.GetVoucherById(id).getLoan().getId();
		voucherService.updateVoucher(voucher);
		
		response.sendRedirect("/Vouchers/all/"+loanid);
	}
	
	// -----------------------------------------------------------------------------
	
	// paid Voucher 
	@RequestMapping (method = RequestMethod.GET , value="/Vouchers/Paid/{id}")
	public void PaidVoucher(@PathVariable int id , HttpServletResponse response) throws IOException
	{
		Vouchers voucher = voucherService.GetVoucherById(id);
		voucher.setStatus("paid");
		
		voucherService.updateVoucher(voucher);
		int  loanid = voucher.getLoan().getId();
		
		response.sendRedirect("/Loans/CheckCloseLoan/"+loanid);
		 
	}
	
	public boolean checkAddVoucher(int id)
	{
		int numberOfVoucher = Integer.parseInt(loanService.getOneByID(id).getNumberOfVoucher()) ;
		List<Vouchers> NumberofloanVocher = voucherService.getVoucherForThisLoan(id);
		if(numberOfVoucher>NumberofloanVocher.size())
			return true;
		else 
			return false;
	}

	
	
	//--------------------------------------------
	
	public ModelAndView addVoucherSequence(int loanId , int sequence) {		
		ModelAndView mav = new ModelAndView("Vouchers/AddVoucher");
		Loans myloan = loanService.getOneByID(loanId);
		List<Clients> allclient = clientService.GetAllClientsNoPage();
		mav.addObject("voucher", new Vouchers());
		mav.addObject("allclient", allclient);
		mav.addObject("myloan", myloan);
		mav.addObject("id", loanId);
		mav.addObject("seq", sequence);
		return mav;	
	}	
	
	@RequestMapping(method = RequestMethod.POST, value ="/Vouchers/add/sequence/{loanid}/{sequenceNumber}")
	public ModelAndView addVoucherSequenceResponse(@PathVariable int loanid , @PathVariable int sequenceNumber,@ModelAttribute Vouchers voucher) {
		//	step #0  check voucher info 
		//voucher.checkinfo(); // date / values / total loan value / below zero 
		//step #1 add the voucher 
		Loans loan=loanService.getOneByID(loanid);
		voucher.setStatus("Open");
		voucher.setLoan(loan);
		voucherService.addVoucher(voucher);
		//step #2 check the remaining voucher to add 
		//stop condition -- all vouchers added 
		if(sequenceNumber <= 0 ) {
			ModelAndView mav = new ModelAndView("Vouchers/AllVouchers");
			List<Vouchers> allvouchers= voucherService.getVoucherForThisLoan(loanid);
			mav.addObject("vouchers", allvouchers);
			return mav; 
		}
		//else we add the remaining vouchers 
		else {
			int nextSequence = sequenceNumber - 1 ; 
			return addVoucherSequence(loanid,nextSequence);
		}
	}
	
	
	//conflict resolver methods 
	
	private ModelAndView conflictResolver(int choice, int loanId,int numOfRemaining) {
		switch(choice) {
			//case 1 : remove everything 
			case 1 : {
				removeLoanPermenant(loanId); 
				//redirect to all loans 
				return this.loansController.ShowAllOpenLoans(0);
			}
			//case 2 : remove vouchers only and re-enter them 
			case 2 : {
				removeVouchersOnly(loanId);
				return addVoucherSequence(loanId,Integer.valueOf(loanService.getOneByID(loanId).getNumberOfVoucher())); 
			}
			//case 3 : fill the remaining vouchers with zero values 
			case 3 : {
				fillVouchersZeroValues(loanId,numOfRemaining);
				return this.allVouchers(loanId);
			}
			default : return null;
		}
	}
	 
	//remove the loan and its vouchers 
	private void removeLoanPermenant(int loanId) {
		List<Vouchers> loanVouchers = voucherService.getVoucherForThisLoan(loanId);
		for(Vouchers voucher : loanVouchers) {
			voucherService.deleteVoucher(voucher.getVoucherID());
		}
		loanService.DeleteLoan(loanId);
	}
	
	//remove the loan vouchers only 
	private void removeVouchersOnly(int loanId) {
		List<Vouchers> loanVouchers = voucherService.getVoucherForThisLoan(loanId);
		for(Vouchers voucher : loanVouchers) {
			voucherService.deleteVoucher(voucher.getVoucherID());
		}
	}
	
	//create new voucher Objects and fill them with zero values and link them to the loan object 
	private void fillVouchersZeroValues(int loanId,int numOfRemainingVouchers) {
		for(int i  = 0 ; i < numOfRemainingVouchers ; i++) {
		Vouchers voucher = new Vouchers();
		voucher.setLoan(loanService.getOneByID(loanId));
		voucher.initWithZeroValues(); 
		voucherService.addVoucher(voucher);
		}
	}

	//end of conflict resolvers
	
	//data check section // 
	private String validateVoucherInfo(Vouchers voucher) {
		if(!checkDateValidation(voucher.getVoucherDate())){
			return "voucher date should be bigger than the current date";
		}
		if(voucher.getClient() == null ) {
			return "client is empty ";
		}
		if(voucher.getFundingRatio().equalsIgnoreCase("") || voucher.getFundingRatio().equalsIgnoreCase(" ")) {
			return "funcing ratio is not valid";
		}
		if(!checkFloatOrDoubleData(voucher.getFundingRatio())) {
			return "funcing ratio is not valid";
		}
		if(voucher.getLoan() == null ) {
			return "loan not found";
		}
		if(!checkIntegerData(voucher.getNetAmmount())) {
			return "net amount should be an integer value";
		}
		//continue from here ---------------------------------- data check not finished 
		return "ok";
	}
	
	
	
	//this method checks for the float or double values // the point character is allowed once only (example : 1.5)
	public boolean checkFloatOrDoubleData(String data) {
		int pointCounter = 0 ; 
		for(char c : data.toCharArray()) {
			if(!Character.isDigit(c) || c != '.') {
				return false ; 
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
	
	//only allow integer values
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
		LocalDateTime CurrentDate = MasterService.getCurrDateTime();
		LocalDateTime voucherDate = LocalDateTime.parse(date);
		if(CurrentDate.isAfter(voucherDate)) {
			return false ; 
		}
		return true ; 
	}
	
	//end of data check section // 
	
	
}
