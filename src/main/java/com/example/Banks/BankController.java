package com.example.Banks;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.Banks;

@RestController
public class BankController {

	@Autowired
	private BankService banksercice;
	
	
	@RequestMapping("Banks")
	public List<Banks> GetAllBanks ()
	{
		return banksercice.GetAllBanks();
	}
	
	@RequestMapping("/Banks/{id}")
	public Banks GetBank(@PathVariable int id)
	{
		Optional<Banks> bank=banksercice.GetBank(id);
		if(bank.isPresent())
		{
			return bank.get();
		}
		else 
			throw new RuntimeException("Bank"+ id + "is not Exist " );
	}
	
	@RequestMapping(method = RequestMethod.POST , value="/addBank")
	public void addBank(@RequestBody Banks bank)
	{
		banksercice.addBank(bank);
	}
	
	@RequestMapping(method = RequestMethod.PUT , value="/Banks")
	public void updateBank(@RequestBody Banks bank)
	{
		banksercice.updateBank(bank);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE , value="/Banks/{id}")
	public void deleteBank(@PathVariable int id)
	{
		banksercice.deleteBank(id);
		
	}
	
}
