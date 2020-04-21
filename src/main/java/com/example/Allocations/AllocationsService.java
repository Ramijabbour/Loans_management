package com.example.Allocations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllocationsService {

    @Autowired
    private AllocationsRepository allocationsRepository;

    public List<Allocations> getAllAllocations() {
        return allocationsRepository.findAll();
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
			if(allocationsRepository.findById(allocation.getAllocationID()) != null) {
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
			if(allocation.getAllocationID() ==id){
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
