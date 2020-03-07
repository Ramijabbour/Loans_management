package com.example.Clients;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Clients {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ClientID ;
	private String ClientName ;
	private int identity_number;
	
	
	public Clients() {}

	
	public Clients( String clientName, int identity_number) {
		ClientName = clientName;
		this.identity_number = identity_number;
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


	public int getIdentity_number() {
		return identity_number;
	}


	public void setIdentity_number(int identity_number) {
		this.identity_number = identity_number;
	}
		
}
