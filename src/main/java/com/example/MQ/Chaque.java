package com.example.MQ;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chaque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    public int CheckId;
    public String FirstBank;
    public int FirstBankSW;
    public String SecondBank;
    public int SecondBankSW;
    public double Amount;
    public boolean active = false ; 
    
    
    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Chaque() {
    }

    public Chaque(int id , String firstBank, int firstBankSW, String secondBank, int secondBankSW, double amount) {
        this.CheckId = id ; 
    	FirstBank = firstBank;
        FirstBankSW = firstBankSW;
        SecondBank = secondBank;
        SecondBankSW = secondBankSW;
        Amount = amount;
    }

    public int getCheckId() {
        return CheckId;
    }

    public void setCheckId(int checkId) {
        CheckId = checkId;
    }

    public String getFirstBank() {
        return FirstBank;
    }

    public void setFirstBank(String firstBank) {
        FirstBank = firstBank;
    }

    public int getFirstBankSW() {
        return FirstBankSW;
    }

    public void setFirstBankSW(int firstBankSW) {
        FirstBankSW = firstBankSW;
    }

    public String getSecondBank() {
        return SecondBank;
    }

    public void setSecondBank(String secondBank) {
        SecondBank = secondBank;
    }

    public int getSecondBankSW() {
        return SecondBankSW;
    }

    public void setSecondBankSW(int secondBankSW) {
        SecondBankSW = secondBankSW;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

}
