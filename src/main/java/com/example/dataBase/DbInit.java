package com.example.dataBase;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.models.Allocations;
import com.example.models.Banks;
import com.example.models.FinanceType;	
import com.example.models.LoansType;

@Service
public class DbInit implements CommandLineRunner{
	
	
	private FinanceTypeRepository finanaceTypeRepository ; 
	private BanksRepository banksRepository ; 
	private AllocationsRepository allocationsRepository ;
	private LoansTypeRepository loansTypeRepository ; 
	
	public DbInit(FinanceTypeRepository finanaceTypeRepo,BanksRepository banksRepo,AllocationsRepository allocationsRepo,LoansTypeRepository loansTypeRepo) {
		this.finanaceTypeRepository = finanaceTypeRepo ; 
		this.banksRepository = banksRepo ; 
		this.allocationsRepository = allocationsRepo ; 
		this.loansTypeRepository = loansTypeRepo ;
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		
		//Finance types data these are constants  do not change or add //////  
		FinanceType ft1 = new FinanceType("استراتيجي",100,45);
		FinanceType ft2 = new FinanceType("قصير الأمد",80,180);;
		FinanceType ft3 = new FinanceType("استراتيجي",100,1800);
		List <FinanceType> finanaceTypes = Arrays.asList(ft1,ft2,ft3);
		this.finanaceTypeRepository.saveAll(finanaceTypes);
		//finance types end 
			
		Banks bank1 = new Banks("المصرف العقاري","فرع دمشق","#3301",10000000) ;
		Banks bank2 = new Banks("المصرف العقاري","فرع حمص","#3302",7000000) ;
		Banks bank3 = new Banks("المصرف التجاري","فرع دمشق","#3303",20000000) ;
		Banks bank4 = new Banks("المصرف التجاري","فرع حمص","#3304",15000000) ;
		Banks bank5 = new Banks("المصرف الزراعي","فرع دمشق","#3305",13000000) ;
		Banks bank6 = new Banks("لمصرف الزراعي","فرع حمص","#3306",17000000) ;
		List<Banks> banks = Arrays.asList(bank1,bank2,bank3,bank4,bank5,bank6);
		this.banksRepository.saveAll(banks);
		
		Allocations allocation1 = new Allocations("1-1-2018",8500000,bank1);
		Allocations allocation2 = new Allocations("1-1-2018",14000000,bank2);
		Allocations allocation3 = new Allocations("1-1-2018",9000000,bank3);
		Allocations allocation4 = new Allocations("1-1-2018",15000000,bank4);
		Allocations allocation5 = new Allocations("1-1-2018",17000000,bank5);
		Allocations allocation6 = new Allocations("1-1-2018",12000000,bank6);
		Allocations allocation7 = new Allocations("1-1-2019",10000000,bank1);
		Allocations allocation8 = new Allocations("1-1-2019",9000000,bank2);
		Allocations allocation9 = new Allocations("1-1-2019",7000000,bank3);
		Allocations allocation10 = new Allocations("1-1-2019",8000000,bank4);
		Allocations allocation11 = new Allocations("1-1-2019",7000000,bank5);
		Allocations allocation12 = new Allocations("1-1-2019",5000000,bank6);
		Allocations allocation13 = new Allocations("1-1-2020",10000000,bank1);
		Allocations allocation14 = new Allocations("1-1-2020",7000000,bank2);
		Allocations allocation15 = new Allocations("1-1-2020",20000000,bank3);
		Allocations allocation16 = new Allocations("1-1-2020",15000000,bank4);
		Allocations allocation17 = new Allocations("1-1-2020",13000000,bank5);
		Allocations allocation18 = new Allocations("1-1-2020",17000000,bank6);
		
		List<Allocations> allocations = Arrays.asList(allocation1,allocation2,allocation3,allocation4,allocation5,allocation6
				,allocation7,allocation8,allocation9,allocation10,allocation11,allocation12
				,allocation13,allocation14,allocation15,allocation16,allocation17,allocation18);
		
		this.allocationsRepository.saveAll(allocations);
		
		
		LoansType loantype1 = new LoansType("مرخص");
		LoansType loantype2 = new LoansType("معفى");
		List<LoansType> loansTypes = Arrays.asList(loantype1,loantype2);
		this.loansTypeRepository.saveAll(loansTypes); 
		//مرخص و معفى 
	} 
	
	
}
