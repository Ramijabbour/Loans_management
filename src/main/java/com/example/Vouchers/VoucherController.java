package com.example.Vouchers;

import java.io.IOException;
import java.util.ArrayList;
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

import com.example.Banks.Banks;
import com.example.Clients.ClientService;
import com.example.Clients.Clients;
import com.example.Loans.LoanService;
import com.example.Loans.Loans;

@RestController
public class VoucherController {

	@Autowired
	private VoucherService voucherService; 
	@Autowired
	private LoanService loanService ;
	@Autowired
	private ClientService clientService ; 
	
	
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
		response.sendRedirect("/Loans/all/Open");
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
	
	
	
	
	@RequestMapping(method = RequestMethod.GET ,value = "/Vouchers/req/add/{lid}")
	public ModelAndView addVoucherRequest(@PathVariable int lid) {
		ModelAndView mav = new ModelAndView("Vouchers/addSequence");
		mav.addObject("lid", lid);
		Loans loan = loanService.getOneByID(lid);
		List<Vouchers> vouchersList = new ArrayList<Vouchers>(); 
		for(int i = 0 ; i < Integer.valueOf(loan.getNumberOfVoucher());i++) {
			vouchersList.add(new Vouchers());
		}
		mav.addObject("vlist",vouchersList);
		return mav; 
	}
	
	@RequestMapping(method = RequestMethod.POST ,value = "/Vouchers/add/sequence/{lid}")
	public ModelAndView addVoucherSequence(@ModelAttribute List<Vouchers> vouchersList ,@PathVariable int lid) {
		
		for(Vouchers voucher : vouchersList ) {
			System.out.println("voucher recieved with ammount  : "+voucher.getVoucherAmmount());
		}
		
		return null ; 
	}
	
	
	
	
	
	
}
