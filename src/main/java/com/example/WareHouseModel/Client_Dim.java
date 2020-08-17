package com.example.WareHouseModel;


import javax.persistence.*;

import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;



@Entity
public class Client_Dim {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id ;

	public String clientName;
	
	public String address ;
	
	public String gender ;
	
	public String Married ;
	
	public String NumberOFChilderen ;
	
	public String Age ;
	
	public String income ;
	
	public Client_Dim() {}

	public Client_Dim(int id, String clientName, String address, String gender, String married,
			String numberOFChilderen, String age, String income) {
		super();
		this.id = id;
		this.clientName = clientName;
		this.address = address;
		this.gender = gender;
		Married = married;
		NumberOFChilderen = numberOFChilderen;
		Age = age;
		this.income = income;
	}

	public int getId() {
		return id;
	}

	public void setId(int clientID) {
		id = clientID;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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




	public String getIncome() {
		return income;
	}




	public void setIncome(String income) {
		this.income = income;
	}
	
	
}