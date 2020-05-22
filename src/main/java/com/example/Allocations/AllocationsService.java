package com.example.Allocations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Banks.Banks;
import com.example.CloseLoans.CloseLoans;
import com.example.SiteConfig.MasterService;
import com.example.SiteConfig.SiteConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AllocationsService {

    @Autowired
    private AllocationsRepository allocationsRepository;

    public List<Allocations> getAllAllocations(int PageNumber) {
        Pageable paging = PageRequest.of(PageNumber, SiteConfiguration.getPageSize());
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
	
	
	
	
	public boolean CheckDateMoreThanYear(Banks bank ) throws ParseException 
	{
		List<Allocations> allAllocation =getBankAllocations(bank);
		//System.out.println("all allocation for bank is "+allAllocation.size());
		if(allAllocation.isEmpty()) {
			//System.out.println("from allocation 1");
			return true;
			}
		Allocations lastAllocation =allAllocation.get(allAllocation.size()-1);
		//System.out.println("last allocation for "+bank.getBankName()+" "+lastAllocation.getAllocationDate());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");	 
		//System.out.println("form in table " +lastAllocation.getAllocationDate());
	    Date firstDate = sdf.parse(lastAllocation.getAllocationDate());
	//	System.out.println("first date "+ firstDate);
		
		Date date = new Date();
		//System.out.println("second date "+sdf.format(date));
	   long diffInMillies = Math.abs(date.getTime() - firstDate.getTime());
	   long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	//   System.out.println("diff is "+diff);
	   if(diff>=366)
		    return true;
	   
	   
	   return false ;
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

	public List<Allocations> getallAllocationsNoPage(){
		return this.allocationsRepository.findAll() ; 
	}
	
	
	public Allocations getBankAllocationWithDate(Banks bank,int year){
 		for(Allocations allocation : this.allocationsRepository.findAll()) {
			if(allocation.getBank().getBankID() == bank.getBankID() ) {
				if(Integer.valueOf(MasterService.getYearFromStringDate(allocation.getAllocationDate())) == year)
				return allocation ; 
			}
		}
 		return null ; 
	}
	
	public Page<Allocations> getAllocationsChuck(int pageNum,Page<Allocations> prevPage){
		if(prevPage == null) {
			Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAllocationsAnalyticsPageSize(), Sort.by("id"));
			Page<Allocations> modelPage = this.allocationsRepository.findAll(paging);
			return modelPage ;
			
			}else if(prevPage.hasNext()) {
				Pageable paging = PageRequest.of(pageNum, SiteConfiguration.getAllocationsAnalyticsPageSize(), Sort.by("id"));
				Page<Allocations> modelPage = this.allocationsRepository.findAll(paging);
				return modelPage ;
			}else {
				return null ; 
			}
	}
	
	
}
