package com.example.Vouchers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
		List<Clients> allclient = clientService.GetAllClients();
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
		response.sendRedirect("/Loans/all");
	}
	
	@RequestMapping(method=RequestMethod.GET , value="/Vouchers/all")
	public ModelAndView allVouchers()
	{
		ModelAndView mav = new ModelAndView("Vouchers/AllVouchers");
		List<Vouchers> allvouchers= voucherService.GetAllVouchers();
		mav.addObject("vouchers", allvouchers);
		return mav; 
	}
	
	
	
	public boolean checkAddVoucher(int id)
	{
		int numberOfVoucher = Integer.parseInt(loanService.getOneByID(id).getNumberOfVoucher()) ;
		System.out.println("number of voucher "+numberOfVoucher);
		int  NumberofloanVocher = voucherService.getLoanVocher(id);
		System.out.println("loan voucher "+NumberofloanVocher );
		if(numberOfVoucher>NumberofloanVocher)
			return true;
		else 
			return false;
	}
	
}
