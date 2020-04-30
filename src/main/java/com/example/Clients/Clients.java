package com.example.Clients;

import ValidContent_Visitor.Visitor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Null;

import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;



@Entity
public class Clients {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id ;
	@Convert(converter = StringEncryptDecryptConverter.class)

	public String ClientName ;
	@Convert(converter = StringEncryptDecryptConverter.class)

	public String ClientType;
	@Column(nullable = true)
	@Convert(converter = StringEncryptDecryptConverter.class)

	public String identity_number;
	@Convert(converter = StringEncryptDecryptConverter.class)

	public String email;
	@Convert(converter = StringEncryptDecryptConverter.class)
	
	public String phone;
	@Convert(converter = StringEncryptDecryptConverter.class)
	
	public String address ;
	@Convert(converter = StringEncryptDecryptConverter.class)
	
	public String gender ;
	
	public Clients() {}






	public Clients(String clientName, String clientType, String identity_number, String email, String phone,
			String address, String gender) {
		
		ClientName = clientName;
		ClientType = clientType;
		this.identity_number = identity_number;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
	}


	public String getClientType() {
		return ClientType;
	}


	public void setClientType(String clientType) {
		ClientType = clientType;
	}


	public int getId() {
		return id;
	}

	public void setId(int clientID) {
		id = clientID;
	}

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String clientName) {
		ClientName = clientName;
	}


	public String getIdentity_number() {
		return identity_number;
	}


	public void setIdentity_number(String identity_number) {
		this.identity_number = identity_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
	public boolean accept(Visitor visitor) { return visitor.visit(this); }}
