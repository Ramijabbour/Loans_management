package com.example.Clients;

import ValidContent_Visitor.Visitor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Null;



@Entity
public class Clients {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int ClientID ;
	public String ClientName ;
	public String ClientType;
	@Column(nullable = true)
	public String identity_number;
	
	
	public Clients() {}


	public Clients(String clientName, String clientType, String identity_number) {
		
		ClientName = clientName;
		ClientType = clientType;
		this.identity_number = identity_number;
	}





	public String getClientType() {
		return ClientType;
	}


	public void setClientType(String clientType) {
		ClientType = clientType;
	}


	public int getClientID() {
		return ClientID;
	}

	public void setClientID(int clientID) {
		ClientID = clientID;
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

	public boolean accept(Visitor visitor) { return visitor.visit(this); }}
