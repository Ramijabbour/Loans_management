package com.example.WareHouseModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClientLoan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id ;
	
	public int TotalAmmount;
    
    public double NetAmmount;

    private String status; 
	
	public String address ;
	
	public String gender ;
	
	public String Married ;
	
	public int NumberOFChilderen ;
	
	public int Age ;

	public String Loan_Type ;

	public String Finance_Type ; 
	
	
	public int income ;

	public String result ;
	
	
	

	public ClientLoan() {}

	public ClientLoan(int totalAmmount, double netAmmount, String status, String address, String gender, String married,
			int numberOFChilderen, int age, String loan_Type, String finance_Type, int income, String result) {
		super();
		TotalAmmount = totalAmmount;
		NetAmmount = netAmmount;
		this.status = status;
		this.address = address;
		this.gender = gender;
		Married = married;
		NumberOFChilderen = numberOFChilderen;
		Age = age;
		Loan_Type = loan_Type;
		Finance_Type = finance_Type;
		this.income = income;
		this.result = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMarried() {
		return Married;
	}

	public void setMarried(String married) {
		Married = married;
	}

	
	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getLoan_Type() {
		return Loan_Type;
	}

	public void setLoan_Type(String loan_Type) {
		Loan_Type = loan_Type;
	}

	public String getFinance_Type() {
		return Finance_Type;
	}

	public void setFinance_Type(String finance_Type) {
		Finance_Type = finance_Type;
	}



	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getTotalAmmount() {
		return TotalAmmount;
	}

	public void setTotalAmmount(int totalAmmount) {
		TotalAmmount = totalAmmount;
	}

	

	public double getNetAmmount() {
		return NetAmmount;
	}

	public void setNetAmmount(double netAmmount) {
		NetAmmount = netAmmount;
	}

	public int getNumberOFChilderen() {
		return NumberOFChilderen;
	}

	public void setNumberOFChilderen(int numberOFChilderen) {
		NumberOFChilderen = numberOFChilderen;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	} 
	
   
    
    
    
	
}
