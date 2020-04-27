package com.example.settelmets.Models;

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
    @Convert(converter = IntEncryptDecryptConverter.class)
    private int id;
    
    @Column(nullable = false )
    @Convert(converter = IntEncryptDecryptConverter.class)
    private  int CheckId;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String FirstBankName;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String SecondBankName ; 
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String FirstBranchName;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String FirstBranchCode;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String SecondBranchName;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String SecondBranchCode;
    
    @Column(nullable = false )
    @Convert(converter = DoubleEncryptDecryptConverter.class)
    private  double Amount;
	
    @Column(nullable = false )
    private  LocalDateTime localDateTime = MasterService.getCurrDateTime() ;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String UserName ; 
    
    @Column(nullable = false )
    @Convert(converter = IntEncryptDecryptConverter.class)
    private  int UserID ; 
    
	private boolean active = false ;
	

	public Chaque() {}
	
	public Chaque(int checkId, String firstBankName, String secondBankName, String firstBranchName,
			String firstBranchCode, String secondBranchName, String secondBranchCode, double amount, String userName,
			int userID, boolean active) {
		super();
		CheckId = checkId;
		FirstBankName = firstBankName;
		SecondBankName = secondBankName;
		FirstBranchName = firstBranchName;
		FirstBranchCode = firstBranchCode;
		SecondBranchName = secondBranchName;
		SecondBranchCode = secondBranchCode;
		Amount = amount;
		UserName = userName;
		UserID = userID;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public int getCheckId() {
		return CheckId;
	}

	public String getFirstBankName() {
		return FirstBankName;
	}

	public String getSecondBankName() {
		return SecondBankName;
	}

	public String getFirstBranchName() {
		return FirstBranchName;
	}

	public String getFirstBranchCode() {
		return FirstBranchCode;
	}

	public String getSecondBranchName() {
		return SecondBranchName;
	}

	public String getSecondBranchCode() {
		return SecondBranchCode;
	}

	public double getAmount() {
		return Amount;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public String getUserName() {
		return UserName;
	}

	public int getUserID() {
		return UserID;
	}

	
}
