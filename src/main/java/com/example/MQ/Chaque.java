package com.example.MQ;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.MasterService;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
public class Chaque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;
    @Convert(converter = IntEncryptDecryptConverter.class)
    public int CheckId;
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String FirstBank;
    public int FirstBankSW;
    public String SecondBank;
    public int SecondBankSW;
    public double Amount;
	public LocalDateTime localDateTime ;
    public boolean active = false ;
    


	public Chaque() {
		this.localDateTime = MasterService.getCurrDateTime() ;
    }

    public Chaque(int id , String firstBank, int firstBankSW, String secondBank, int secondBankSW, double amount) {
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
    
    public boolean isActive() {
  		return active;
  	}

  	public void setActive(boolean active) {
  		this.active = active;
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

	public void setId(int id) {
		this.id = id;
	}
  	
  	

}
