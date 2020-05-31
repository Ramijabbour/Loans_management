package com.example.BankBranches;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;
import com.example.SiteConfig.SiteConfiguration;


@Service
public class BrancheService {

	@Autowired
	BrancheRepository brancheRepository;
	
	public void addBranche(Branches branche)
	{
		this.brancheRepository.save(branche);
	}
	
	public List<Branches> getAllBranche(int PageNumber)
	{
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));		
		Page<Branches> pagedResult = this.brancheRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Branches>();
        }
	
	}
	
	
	public List<Branches> getAllBrancheNoPage()
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
			if(brancheRepository.findById(branche.getId()) != null) {
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
			if(branche.getId()==id)
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
	//------search

	//------search
	public List<Branches> SearchbybrancheCode(int PageNumber,String brancheCode){
		Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Slice<Branches> pagedResult = this.brancheRepository.findByBrancheCode(brancheCode,paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Branches>();
		}
	}
	
	public int getBranchesCount() {
		return this.brancheRepository.getBranchesCount() ; 
	}
}
