package com.example.WareHouseService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.WareHouseModel.Fact_Table;
import com.example.WareHouseRepository.FactRepository;

@Service
public class FactTableService {

	@Autowired 
	FactRepository factRepo ; 
	
	public List<Fact_Table> filterFact()
	{
		List<Fact_Table>All = factRepo.findAll();
		List<Fact_Table> filterFact = new ArrayList<Fact_Table>();
		
		for(Fact_Table f : All)
		{
			if(!clientExist(filterFact, f.getClient().getId()))
			{
			List<Fact_Table> temp =  getFactforThisClient(All, f.getClient().getId());
			Fact_Table temp2 = temp.get(temp.size()-1);
			filterFact.add(temp2);
			}
		}
		
		/*for(Fact_Table f : filterFact)
		{
			System.out.println(f.getClient().getId() +"  "+ f.getId());
		}*/
		return filterFact;
		
	}
	
	public List<Fact_Table> getFactforThisClient(List<Fact_Table> all,int id)
	{
		List<Fact_Table> factforClient = new ArrayList<Fact_Table>();
		for(Fact_Table f : all)
		{
			if(id == f.getClient().getId())
			{
				factforClient.add(f);
			}
		}
		return factforClient; 
	}
	
	
	public boolean clientExist (List<Fact_Table> all,int id )
	{
		for(Fact_Table f : all)
		{
			if(f.getClient().getId()==id)
				return true ;
		}
		return false ; 
	}
	
	
	
}
