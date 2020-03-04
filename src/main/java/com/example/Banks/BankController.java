package com.example.Banks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Banks;

@RestController
public class BankController {

	@Autowired
	private BankService banksercice;
	
	
	@RequestMapping("AllBanks")
	public List<Banks> GetAllBanks ()
	{
		return banksercice.GetAllBanks();
	}
	
	
	@RequestMapping(method = RequestMethod.POST , value="/addBank")
	public void addBank(@RequestBody Banks bank)
	{
		banksercice.addBank(bank);
		
	}
	
}
