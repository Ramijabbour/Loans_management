package com.example.MQ;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.MasterService;
import com.example.aspect.EncryptDecrypt.DoubleEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class SettledChaque {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	 	@Convert(converter = IntEncryptDecryptConverter.class)
	 	private int id;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = IntEncryptDecryptConverter.class)
	 	private int CheckId;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String FirstBank;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = IntEncryptDecryptConverter.class)
	 	private int FirstBankSW;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String SecondBank;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = IntEncryptDecryptConverter.class)
	 	private int SecondBankSW;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = DoubleEncryptDecryptConverter.class)
	 	private double Amount;

	 	@Column(nullable = false )
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
