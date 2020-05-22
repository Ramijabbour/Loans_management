package com.example.Clients;

import ValidContent_Visitor.Visitor;

import javax.persistence.*;

import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;



@Entity
@Table(name = "Clients",indexes = {@Index(name = "index_clientName",  columnList="clientName", unique = false)})
public class Clients {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id ;
	@Convert(converter = StringEncryptDecryptConverter.class)

	public String clientName;
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
		
		this.clientName = clientName;
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
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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
