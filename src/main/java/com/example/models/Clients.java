package com.example.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;



@Entity
public class Clients {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ClientID ;
	
	private String ClientName ;

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
		
}
