package com.example.Vouchers;

import java.io.IOException;
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
	public ModelAndView addVoucherSequenceForSchedualLoan(int Oldloan ,int NewLoan, int sequence) {		
		ModelAndView mav = new ModelAndView("Vouchers/AddVoucherForSchedualLoan");
		Loans newloan = loanService.getOneByID(NewLoan);
		List<Clients> allclient = clientService.GetAllClientsNoPage();
		
		List<Vouchers> VoucherForThisLoan = voucherService.getVoucherForThisLoan(Oldloan);
		System.out.println("number of voucher "+ VoucherForThisLoan.size());
		
		System.out.println("sqquence "+sequence);
		Vouchers voucher = VoucherForThisLoan.get(sequence);
		System.out.println("voucher "+voucher.getNetAmmount());
		mav.addObject("voucher", voucher);
		mav.addObject("allclient", allclient);
		mav.addObject("myloan", newloan);
		mav.addObject("id", NewLoan);
		mav.addObject("oldid", Oldloan);
		mav.addObject("seq", sequence);
		
		return mav;	
	}	
	
	@RequestMapping(method = RequestMethod.POST, value ="/Vouchers/add/Schedual/Loan/sequence/{loanid}/{OldLoan}/{sequenceNumber}")
	public ModelAndView addVoucherSequenceResponseForSchedualLoan(@PathVariable int loanid ,@PathVariable int OldLoan, @PathVariable int sequenceNumber,@ModelAttribute Vouchers voucher) {
		
		/* step #0 check if total loan value is bigger than total vouchers value  
		 * conflict resolver will interrupt in that case
		 * we redirect to resolver view and it should return 
		 * a choice to send to conflict resolver method */ 
		
		Loans loan=loanService.getOneByID(loanid);
		voucher.setLoan(loan);
		
		/*step #01 if the total vouchers value is less than the loan value we proceed 
		 * check if the voucher info are valid 
		*/
		String dataValidationResult =this.voucherService.validateVoucherInfo(voucher) ;  
		if(!dataValidationResult.equalsIgnoreCase("ok")){
			//return error view with reason 
			//then return to the same voucher adding sequece  
			ModelAndView mav = new ModelAndView("Errors/voucherDataError");
			mav.addObject("msg", dataValidationResult);
			mav.addObject("loanid",loanid);
			mav.addObject("seq",sequenceNumber);
			return mav ; 
		}
		
		//step #1 add the voucher 
		voucher.setStatus("Open");
		
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
			return addVoucherSequenceForSchedualLoan(OldLoan,loanid,nextSequence);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
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
		
		/* step #0 check if total loan value is bigger than total vouchers value  
		 * conflict resolver will interrupt in that case
		 * we redirect to resolver view and it should return 
		 * a choice to send to conflict resolver method */ 
		
		Loans loan=loanService.getOneByID(loanid);
		if(!this.voucherService.checkLoanVouchersTotalValue(loan, this.voucherService.getVouchersValueForLoan(loanid) + Integer.valueOf(voucher.getNetAmmount()))) {
			return this.interruptVoucherAddingSequence(sequenceNumber, loanid, loan);
		}
		voucher.setLoan(loan);
		
		/*step #01 if the total vouchers value is less than the loan value we proceed 
		 * check if the voucher info are valid 
		*/
		String dataValidationResult =this.voucherService.validateVoucherInfo(voucher) ;  
		if(!dataValidationResult.equalsIgnoreCase("ok")){
			//return error view with reason 
			//then return to the same voucher adding sequece  
			ModelAndView mav = new ModelAndView("Errors/voucherDataError");
			mav.addObject("msg", dataValidationResult);
			mav.addObject("loanid",loanid);
			mav.addObject("seq",sequenceNumber);
			return mav ; 
		}
		
		//step #1 add the voucher 
		voucher.setStatus("Open");
		
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
		
	@RequestMapping(method = RequestMethod.GET , value = "/Vouchers/add/seq/conf/{loanId}/{numOfRemaining}/{choice}")
	public ModelAndView conflictResolver(@PathVariable int choice,@PathVariable int loanId,@PathVariable int numOfRemaining) {
		switch(choice) {
			//case 0 re-enter the voucher
			case 0 : {
			return addVoucherSequence(loanId,numOfRemaining);
			}	
			//case 1 : remove everything 
			case 1 : {
				this.voucherService.removeLoanPermenant(loanId); 
				//redirect to all loans 
				return this.loansController.ShowAllOpenLoans(0);
			}
			//case 2 : remove vouchers only and re-enter them 
			case 2 : {
				this.voucherService.removeVouchersOnly(loanId);
				return addVoucherSequence(loanId,Integer.valueOf(loanService.getOneByID(loanId).getNumberOfVoucher())); 
			}
			
			//this case is commented because the new voucher may have bigger value than the loan itself so we cannot accept it 
			/*//case 3 : fill the remaining vouchers with zero values 
			case 3 : {
				this.voucherService.fillVouchersZeroValues(loanId,numOfRemaining);
				return this.allVouchers(loanId);
			}*/
			default : return new ModelAndView("Errors/VoucherPathError");
		}
	}
	
	private ModelAndView interruptVoucherAddingSequence(int sequenceNumber, int loanid, Loans loan) {
		ModelAndView mav = new ModelAndView("Vouchers/conflictResolver");
		mav.addObject("loanid",loanid);
		mav.addObject("numofremain",sequenceNumber );
		return mav ; 
	}
	
	//end of conflict resolvers
	
	//data check section // 
	
	@RequestMapping(method = RequestMethod.GET ,value="/Vouchers/add/data/err/{loanid}/{sequence}/{choice}")
	public ModelAndView handleVoucherDataErr(@PathVariable int loanid,@PathVariable int sequence,@PathVariable int choice)
	{
		switch(choice) {
		//re-enter the same voucher 
		case 0 : {
			return this.addVoucherSequence(loanid, sequence);
		}
		//re-enter all vouchers 
		case 1 : {
			this.voucherService.removeVouchersOnly(loanid);
			return addVoucherSequence(loanid,Integer.valueOf(loanService.getOneByID(loanid).getNumberOfVoucher()));
		}
		//revoke the loan and vouchers
		case 2 : {
			this.voucherService.removeLoanPermenant(loanid); 
			//redirect to all loans 
			return this.loansController.ShowAllOpenLoans(0);
		}	
		
		default : return new ModelAndView("Errors/VoucherPathError");
		}
	}
	
	//end of data check section // 
	
	
}
