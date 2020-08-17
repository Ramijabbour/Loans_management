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
	
	public String TotalAmmount;
    
    public String NetAmmount;

    private String status; 
	
	public String address ;
	
	public String gender ;
	
	public String Married ;
	
	public String NumberOFChilderen ;
	
	public String Age ;

	public String Loan_Type ;

	public String Finance_Type ; 
	
	
	public String income ;

	public String result ;
	
	
	

	public ClientLoan() {}

	public ClientLoan(String totalAmmount, String netAmmount, String status, String address, String gender, String married,
			String numberOFChilderen, String age, String loan_Type, String finance_Type, String income, String result) {
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

	public String getTotalAmmount() {
		return TotalAmmount;
	}

	public void setTotalAmmount(String totalAmmount) {
		TotalAmmount = totalAmmount;
	}

	public String getNetAmmount() {
		return NetAmmount;
	}

	public void setNetAmmount(String netAmmount) {
		NetAmmount = netAmmount;
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

	public String getNumberOFChilderen() {
		return NumberOFChilderen;
	}

	public void setNumberOFChilderen(String numberOFChilderen) {
		NumberOFChilderen = numberOFChilderen;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
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

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	} 
	
   
    
    
    
	
}
