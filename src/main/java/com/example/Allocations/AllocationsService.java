package com.example.Allocations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.SiteConfiguration;
import com.example.Banks.Banks;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllocationsService {

    @Autowired
    private AllocationsRepository allocationsRepository;

    public List<Allocations> getAllAllocations(int PageNumber) {
        Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize(), Sort.by("id"));
		Page<Allocations> pagedResult = this.allocationsRepository.findAll(paging);
		if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Allocations>();
        }
    }

    
    public void addAllocation(Allocations allocation)
    {
    	allocationsRepository.save(allocation);
    }

    
    public void DeleteAllocation(int id )
    {
    	allocationsRepository.deleteById(id);
    }
    
    
	public void updateAllocation(Allocations allocation) {
		try {
			if(allocationsRepository.findById(allocation.getId()) != null) {
					allocationsRepository.save(allocation); 
				}
		}catch(Exception e ) {
			System.out.println("NullPointerException Handled at Allocation Service / Update Allocation -- call for null Allocation ");
		}
	}

	
	public Allocations getAllocationById(int id)
	{
		List<Allocations> allAllocations = this.allocationsRepository.findAll() ; 
		if(allAllocations.isEmpty()) {
			System.out.println("empty AllocationList ");
			return null ;  
		}
		for(Allocations allocation : allAllocations) {
			if(allocation.getId() ==id){
				return allocation  ; 
			}
		}
		System.out.println("requested allocation not found ");
		return null ; 
	 
	}

	
	public List<Allocations> getBankAllocations(Banks bank){
		List<Allocations> bankAllocations = new ArrayList<Allocations>();
 		for(Allocations allocation : this.allocationsRepository.findAll()) {
			if(allocation.getBank().getBankID() == bank.getBankID() ) {
				bankAllocations.add(allocation);
			}
		}
		return bankAllocations ; 
	}

}
