package com.example.settelmets.Models;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.example.SiteConfig.MasterService;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;

@Entity
@Table(name = "Chaque",indexes = {@Index(name = "index_checkId",  columnList="checkId", unique = false)})
public class Chaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Convert(converter = IntEncryptDecryptConverter.class)
    private int id;
    
    @Column(nullable = false )
    @Convert(converter = IntEncryptDecryptConverter.class)
    private  int checkId;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String firstBankName;
    
    @Column(nullable = false )
    @Convert(converter = StringEncryptDecryptConverter.class)
    private  String secondBankName;
    
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
    //@Convert(converter = DoubleEncryptDecryptConverter.class)
    private  long Amount;
	
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
			String firstBranchCode, String secondBranchName, String secondBranchCode, long amount, String userName,
			int userID, boolean active) {
		super();
		this.checkId = checkId;
		this.firstBankName = firstBankName;
		this.secondBankName = secondBankName;
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
		return checkId;
	}

	public String getFirstBankName() {
		return firstBankName;
	}

	public String getSecondBankName() {
		return secondBankName;
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

	public long getAmount() {
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
