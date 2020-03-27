package com.example.settelmets;

import org.springframework.stereotype.Service;

@Service
public class SettlementService {

	//@Autowired 
	//private ChecksRepository checkRepository ;  
	
	/*
	 * data model sugg : 
	 * the algorithm input is an integer multidimensional array : 
	 * for each model got from the repo : 
	 * {
	 * 
	 * }
	 * */
	
	//service flow : 
	/*
	 * get all the checks that needs to be settled from the repository 
	 * lock the operation from all users 
	 * insert the checks data to the algorithm 
	 * start the operation 
	 * get settlement output 
	 * mark the checks as settled 
	 * export the operation results as an transfer operations
	 * Log the operation status 
	 * every thing should be locked and final (the model attributes may be final )
	 * */
	
	//method to add checks to the current settlement 
	//return the results of the settlement 
	//in case of non settled checks they are delayed to the next settlement 
	//the checks that are done are marked "done" in the status field 
	//settlement results should be stored in some place 
	
	
}
