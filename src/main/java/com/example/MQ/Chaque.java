package com.example.MQ;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.MasterService;
import com.example.aspect.EncryptDecrypt.DoubleEncryptDecryptConverter;
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
    
    @Column(nullable = false )
    //@Convert(converter = IntEncryptDecryptConverter.class)
    public int FirstBankSW;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String SecondBank;
    
    @Column(nullable = false )
    //@Convert(converter = IntEncryptDecryptConverter.class)
    public int SecondBankSW;
    @Convert(converter = DoubleEncryptDecryptConverter.class)
    public double Amount;
	
    @Column(nullable = false )
    public final LocalDateTime localDateTime = MasterService.getCurrDateTime() ;
    
	public boolean active = false ;
    
    
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
    
    public boolean isActive() {
  		return active;
  	}

  	public void setActive(boolean active) {
  		this.active = active;
  	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
  	
  	

}
