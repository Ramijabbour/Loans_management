package com.example.MQ;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.MasterService;

@Entity
public class SettledChaque {

	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	 	private int id;
	 	private int CheckId;
	 	private String FirstBank;
	 	private int FirstBankSW;
	 	private String SecondBank;
	 	private int SecondBankSW;
	 	private double Amount;

	 	private LocalDateTime localDateTime ; 
	 	
	    public SettledChaque() {
	    	 this.localDateTime = MasterService.getCurrDateTime() ; 
	    }

	    public SettledChaque(int id , String firstBank, int firstBankSW, String secondBank, int secondBankSW, double amount) {
	        this.CheckId = id ; 
	    	FirstBank = firstBank;
	        FirstBankSW = firstBankSW;
	        SecondBank = secondBank;
	        SecondBankSW = secondBankSW;
	        Amount = amount;
	        this.localDateTime = MasterService.getCurrDateTime() ; 
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

		public LocalDateTime getLocalDateTime() {
			return localDateTime;
		}

		public void setLocalDateTime(LocalDateTime localDateTime) {
			this.localDateTime = localDateTime;
		}

		public int getId() {
			return id;
		}
	    
}
