package com.example.Loans;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
		import javax.transaction.Transactional;
		import javax.validation.Valid;

		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.data.repository.query.Param;
		import org.springframework.web.bind.annotation.ModelAttribute;
		import org.springframework.web.bind.annotation.PathVariable;
		import org.springframework.web.bind.annotation.RequestMapping;
		import org.springframework.web.bind.annotation.RequestMethod;
		import org.springframework.web.bind.annotation.RequestParam;
		import org.springframework.web.bind.annotation.RestController;
		import org.springframework.web.servlet.ModelAndView;

import com.example.ServicesPool;
import com.example.BankBranches.Branches;
		import com.example.Banks.Banks;
		import com.example.Clients.Clients;
		import com.example.CloseLoans.CloseLoans;
		import com.example.FinanceType.FinanceType;
		import com.example.LoansType.LoansType;
		import com.example.OpenLoans.OpenLoans;
		import com.example.ReScheduleLoans.ReScheduleLoans;
		import com.example.SiteConfig.SiteConfiguration;
import com.example.Vouchers.VoucherController;

@RestController
public class LoansController {

	@Autowired
	private ServicesPool servicePool ; 

	@Autowired
	VoucherController voucherController ;
	
	//get All Open Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/Open")
	public ModelAndView ShowAllOpenLoans(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Loans/AllOpenLoans");
		List<OpenLoans> allOpenloans=servicePool.getOpenLoansService().GetAllOpenLoan(index);
		mav.addObject("allloans",allOpenloans);
		if(allOpenloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav;
	}
	// -------------------------------------------------------------------


	//get All NotConfirmed Open Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/Open/NotConfirmed")
	public ModelAndView ShowAllNotConfirmedOpenLoans(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Loans/AllOpenLoans");
		List<OpenLoans> allOpenloans=servicePool.getOpenLoansService().GetNotConfirmedLoans();
		mav.addObject("allloans",allOpenloans);
		boolean check=true ;
		mav.addObject("check", check);
		/*if(allOpenloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}*/
		return mav;
	}
	// -------------------------------------------------------------------


	//get All Confirmed Open Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/Open/Confirmed")
	public ModelAndView ShowAllConfirmedOpenLoans(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Loans/AllOpenLoans");
		List<OpenLoans> allOpenloans=servicePool.getOpenLoansService().GetConfirmedLoans();
		mav.addObject("allloans",allOpenloans);

		boolean show = true ;
		mav.addObject("show",  show);

		/*if(allOpenloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}*/
		return mav;
	}
	// -------------------------------------------------------------------




	//get All Close Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/Close")
	public ModelAndView ShowAllCloseLoans(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Loans/AllCloseLoan");
		List<CloseLoans> allCloseloans=servicePool.getClosedLoansService().GetAllCloseLoan(index);
		mav.addObject("allloans",allCloseloans);
		if(allCloseloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav;
	}
	// -------------------------------------------------------------------

	//get All ReSchedule Loans ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/all/ReSchedule")
	public ModelAndView ShowAllReScheduleLoans(@Param(value ="index") int index) {
		ModelAndView mav = new ModelAndView("Loans/AllReScheduleLoan");
		List<ReScheduleLoans> allReScheduleloans=servicePool.getResLoansService().getAllReScheduleLoans(index);
		mav.addObject("allloans",allReScheduleloans);
		if(allReScheduleloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav;
	}
	// -------------------------------------------------------------------


	//add loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/addLoan")
	public ModelAndView AddLoan() {
		ModelAndView mav = new ModelAndView("Loans/AddLoan");
		List<Clients> allclient = servicePool.getClientService().GetAllClientsNoPage();
		List<Branches> allbranche=servicePool.getBranchesService().getAllBrancheNoPage();
		List<LoansType> allloanType = servicePool.getLoanTypesService().getAllType();
		List<FinanceType> allfinanceType= servicePool.getFinanceTypeService().getAllFinanceType();
		mav.addObject("allclient",allclient);
		mav.addObject("loan",new Loans());
		mav.addObject("allbranche",allbranche);
		mav.addObject("allloantype",allloanType);
		mav.addObject("allfinancetype",allfinanceType);
		return mav;
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST , value="/Loans/addLoan")
	public ModelAndView addNewLoan(@ModelAttribute Loans loan,HttpServletResponse response) throws IOException {

		Banks bank=loan.getBranche().getBank();

		int Amount=Integer.parseInt(loan.getNetAmmount());
		int bankAlloacation  =Integer.parseInt(bank.getFinancialAllocations());
		int NewAllocation=bankAlloacation-Amount;

		if(NewAllocation>=0) {
			System.out.println("posted to /Loans/addLoan ");
			bank.setFinancialAllocations(Integer.toString(NewAllocation));
			loan.setStatus("NotConfirmed");
			servicePool.getLoansService().addLoan(loan);
			OpenLoans ol =new OpenLoans(loan);
			servicePool.getOpenLoansService().addOpenLoan(ol);
			return this.voucherController.addVoucherSequence(loan.getId(),Integer.valueOf(loan.getNumberOfVoucher())-1);
			//response.sendRedirect("/Loans/all/Open");
		}


		else {
			System.out.println("NullPointerException Handled at loan Service / Add loan -- call for low allocation");
			//response.sendRedirect("/Loans/addLoan/Error");
			ModelAndView mav = new ModelAndView("Loans/Error");
			return mav;
		}
	}
	//ReSchedule Loan-----------------------------------------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/ReSchedule/{id}")
	public ModelAndView ShowRescheduleLoan(@PathVariable int id)
	{
		ModelAndView mav = new ModelAndView("Loans/ReSchedule");
		Loans loan=servicePool.getLoansService().getOneByID(id);
		mav.addObject("loan",loan);
		return mav ;


	}
	@RequestMapping(method = RequestMethod.POST , value="/Loans/ReSchedule/{id}")
	public ModelAndView RescheduleLoan(@ModelAttribute Loans loan,@PathVariable int id) throws IOException
	{
		Loans oldLoan=servicePool.getLoansService().getOneByID(id);

		OpenLoans open = servicePool.getOpenLoansService().getOpenLoanFromLoan(id);
		servicePool.getOpenLoansService().DeleteOpenLoan(open);

		CloseLoans close = new CloseLoans(oldLoan);
		close.setStatus("مرسوم لنقل السلفة الى السلف المجدولة");
		servicePool.getClosedLoansService().addLoan(close);


		Loans NewLoan=new Loans(oldLoan.getName(),oldLoan.getFirstSide(),oldLoan.getSecondSide(),oldLoan.getLoanNumber(),oldLoan.getInterestRate(),oldLoan.getDelayInterestRate(),oldLoan.getClearanceNumber(),oldLoan.getTotalAmmount(),oldLoan.getTotalAmmountAsString()
				,oldLoan.getNetAmmount(),oldLoan.getNetAmmountAsString(),oldLoan.getNumberOfVoucherAsString(),oldLoan.getNumberOfVoucher(),oldLoan.getPurpose(),oldLoan.getClient(),oldLoan.getBranche(),oldLoan.getUser(),oldLoan.getLoanType(),oldLoan.getFinanceType());
		NewLoan.setLoanDate(loan.getLoanDate());
		Date currentDate = new Date();
		NewLoan.setWorkDate(currentDate.toString());
		servicePool.getLoansService().addLoan(NewLoan);

		ReScheduleLoans scheduleLoan=new ReScheduleLoans(NewLoan);
		servicePool.getResLoansService().addLoan(scheduleLoan);

		return this.voucherController.addVoucherSequenceForSchedualLoan(oldLoan.getId(), NewLoan.getId(),Integer.valueOf(oldLoan.getNumberOfVoucher())-1);

		//response.sendRedirect("/Loans/all/ReSchedule?index=0");
	}
	// -------------------------------------------------------------------


	@RequestMapping(method = RequestMethod.GET , value="/Loans/addLoan/Error")
	public ModelAndView ShowErrorLoans() {
		ModelAndView mav = new ModelAndView("Loans/Error");
		return mav;
	}
	// -------------------------------------------------------------------

	//display loan ------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/Loan/{id}")
	public ModelAndView ShowLoan(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Loans/oneLoan");
		Loans l = servicePool.getLoansService().getOneByID(id);
		boolean checkLoan=false ;
		OpenLoans o = servicePool.getOpenLoansService().getOpenLoanFromLoan(id);
		if(o!=null)
			checkLoan=true;

		mav.addObject("checkLoan", checkLoan);
		mav.addObject("loan",l);
		return mav;
	}
	// -------------------------------------------------------------------

	// update Loan--------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET , value="/Loans/edit/{id}")
	public ModelAndView ShowUpdateLoan(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("Loans/UpdateLoan");
		Loans loan=servicePool.getLoansService().getOneByID(id);
		List<Clients> allclient = servicePool.getClientService().GetAllClientsNoPage();
		List<Branches> allbranche=servicePool.getBranchesService().getAllBrancheNoPage();
		List<LoansType> allloanType = servicePool.getLoanTypesService().getAllType();
		List<FinanceType> allfinanceType= servicePool.getFinanceTypeService().getAllFinanceType();
		mav.addObject("allbranche",allbranche);
		mav.addObject("loan",loan);
		mav.addObject("allclient",allclient);
		mav.addObject("allloantype",allloanType);
		mav.addObject("allfinancetype",allfinanceType);
		return mav;
	}


	@RequestMapping(method = RequestMethod.POST , value="/Loans/update/{id}")
	public void UpdateLoan(@Valid Loans loan,HttpServletResponse response) throws IOException {
		System.out.println("posted to /Loans/update/id ");
		servicePool.getLoansService().updateLoan(loan);
		if(servicePool.getOpenLoansService().getOpenLoanFromLoan(loan.getId())!=null)
			response.sendRedirect("/Loans/all/Open?index=0");
		else
			response.sendRedirect("/Loans/all/Close?index=0");
	}



	//----------------------------------------------------------------

	//delete Loan ----------------------------------------------------------
	@RequestMapping(method = RequestMethod.GET, value="/Loans/delete/{id}")
	public void deleteLoan(@PathVariable int id,HttpServletResponse response) throws IOException
	{
		servicePool.getLoansService().DeleteLoan(id);

		if(servicePool.getOpenLoansService().getOpenLoanFromLoan(id)!=null)
			response.sendRedirect("/Loans/all/Open");
		else
			response.sendRedirect("/Loans/all/Close");
	}

	@RequestMapping(method = RequestMethod.GET , value="/Loans/CheckCloseLoan/{id}")
	public void CheckCloseLoan(@PathVariable int id ,HttpServletResponse response) throws IOException
	{
		Loans loan = servicePool.getLoansService().getOneByID(id);
		OpenLoans open=servicePool.getOpenLoansService().getOpenLoanFromLoan(id);
		if(open==null)
		{
			ReScheduleLoans r= servicePool.getResLoansService().getReScheduleLoanFromLoan(id);
			CloseLoans closeloan=new CloseLoans(loan);
			closeloan.setStatus("سلفة مجدولة اغلقت وسددت بالكامل");
			if (servicePool.getVoucherService().AllVoucherPaid(id))
			{
				servicePool.getClosedLoansService().addLoan(closeloan);
				servicePool.getResLoansService().DeleteLoan(r);
			}
			response.sendRedirect("/Vouchers/all/"+loan.getId());
		}
		else {
			CloseLoans closeloan=new CloseLoans(loan);
			closeloan.setStatus("اغلقت وسددت بالكامل");
			if (servicePool.getVoucherService().AllVoucherPaid(id))
			{
				servicePool.getClosedLoansService().addLoan(closeloan);
				servicePool.getOpenLoansService().DeleteOpenLoan(open);
			}
			response.sendRedirect("/Vouchers/all/"+loan.getId());
		}
	}






	@RequestMapping(method = RequestMethod.GET , value="/Loans/Confirme/{id}")
	public void ConfirmLoan(@PathVariable int id, HttpServletResponse response) throws IOException
	{
		Loans Loan=servicePool.getLoansService().getOneByID(id);
		Loan.setStatus("Confirmed");
		servicePool.getLoansService().updateLoan(Loan);
		response.sendRedirect("/Loans/all/Open/Confirmed?index=0");
	}


	//----search

	@RequestMapping(method = RequestMethod.POST , value = "/Loans/Search")
	public ModelAndView SearchByLoanNumber(@Param(value ="index") int index,@RequestParam("search") String loanNumber) {
		ModelAndView mav = new ModelAndView("Loans/SearchLoans");
		List<Loans> allloans = servicePool.getLoansService().SearchByLoanNumber(index,loanNumber);
		mav.addObject("allloans",allloans);
		mav.addObject("searchvar",loanNumber);
		if(allloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}

	@RequestMapping(method = RequestMethod.GET , value = "/Loans/Search/nxtRes/{index}/{searchvar}")
	public ModelAndView searchBankNext(@PathVariable int index,@PathVariable String searchvar ) {
		ModelAndView mav = new ModelAndView("Loans/SearchLoans");
		List<Loans> allloans = servicePool.getLoansService().SearchByLoanNumber(index,searchvar);
		mav.addObject("allbank",allloans);
		mav.addObject("searchvar",searchvar);
		if(allloans.size() > 0 ) {
			SiteConfiguration.addSequesnceVaraibles(mav, index);
		}else {
			SiteConfiguration.addSequesnceVaraibles(mav, -1);
		}
		return mav ;
	}
}
