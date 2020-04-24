package com.example.BankBranches;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;

@Service
public class BrancheService {

	@Autowired
	BrancheRepository brancheRepository;
	
	public void addBranche(Branches branche)
	{
		this.brancheRepository.save(branche);
	}
	
	public List<Branches> getAllBranche()
	{
		return this.brancheRepository.findAll();
	}
	
	public void DeleteBranche (int id)
	{
		this.brancheRepository.deleteById(id);
	}
	
	public void UpdateBracnhe(Branches branche)
	{
		try {
			if(brancheRepository.findById(branche.getBrancheID()) != null) {
					brancheRepository.save(branche); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at branche Service / Update branche -- call for null Branche ");
		}
	}
	
	public Branches getBranche(int id)
	{
		List<Branches> allBranches = brancheRepository.findAll();
		
		for(Branches branche : allBranches)
		{
			if(branche.getBrancheID()==id)
				return branche;
		}
		return null;		
	}
	
	public List<Branches> getBankBranches(Banks bank ){
		List<Branches> allBranches = this.brancheRepository.findAll() ; 
		List<Branches> result = new ArrayList<Branches>() ; 
		for(Branches branch : allBranches) {
			if(branch.getBank().getBankID() == bank.getBankID()) {
				result.add(branch);
			}
		}
		return result; 
	}
}
