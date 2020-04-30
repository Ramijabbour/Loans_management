package com.example.Loans;


import javax.persistence.*;


import ValidContent_Visitor.Visitor;

import com.example.BankBranches.Branches;
import com.example.Banks.Banks;
import com.example.Clients.Clients;
import com.example.FinanceType.FinanceType;
import com.example.LoansType.LoansType;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;
import com.example.security.user.User;
import org.springframework.format.annotation.DateTimeFormat;

//import sun.util.calendar.BaseCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity

public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String Name ;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String FirstSide;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String SecondSide;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String LoanNumber;
    
    public String WorkDate;


    public String LoanDate;

    @Convert(converter = StringEncryptDecryptConverter.class)
    public String InterestRate;

    @Convert(converter = StringEncryptDecryptConverter.class)
    public String DelayInterestRate;

    @Convert(converter = StringEncryptDecryptConverter.class)
    public String ClearanceNumber;

    @Convert(converter = StringEncryptDecryptConverter.class)
    public String TotalAmmount;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String TotalAmmountAsString;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    public String NetAmmount;

    @Convert(converter = StringEncryptDecryptConverter.class)
    private String NetAmmountAsString;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    private String NumberOfVoucherAsString;
    
    @Convert(converter = StringEncryptDecryptConverter.class)
    private String NumberOfVoucher;
    @Convert(converter = StringEncryptDecryptConverter.class)
    private String purpose;

    @ManyToOne
    private Clients client = null;

    @ManyToOne
    private Branches branche = null;

    @ManyToOne
    private User user = null;

    @ManyToOne
    private LoansType loanType = null;

    @ManyToOne
    private FinanceType financeType = null;
    
    
    public Loans() {}
    
    

    public Loans(String name, String firstSide, String secondSide, String loanNumber,
			String interestRate, String delayInterestRate, String clearanceNumber, String totalAmmount,
			String totalAmmountAsString, String netAmmount, String netAmmountAsString, String numberOfVoucherAsString,
			String numberOfVoucher, String purpose, Clients client, Branches branche, User user, LoansType loanType,
			FinanceType financeType) {
		super();
		Name = name;
		FirstSide = firstSide;
		SecondSide = secondSide;
		LoanNumber = loanNumber;
		InterestRate = interestRate;
		DelayInterestRate = delayInterestRate;
		ClearanceNumber = clearanceNumber;
		TotalAmmount = totalAmmount;
		TotalAmmountAsString = totalAmmountAsString;
		NetAmmount = netAmmount;
		NetAmmountAsString = netAmmountAsString;
		NumberOfVoucherAsString = numberOfVoucherAsString;
		NumberOfVoucher = numberOfVoucher;
		this.purpose = purpose;
		this.client = client;
		this.branche = branche;
		this.user = user;
		this.loanType = loanType;
		this.financeType = financeType;
	}



	public int getId() {
        return id;
    }

    public void setId(int loanID) {
        id = loanID;
    }

    public String getFirstSide() {
        return FirstSide;
    }

    public void setFirstSide(String firstSide) {
        FirstSide = firstSide;
    }

    public String getSecondSide() {
        return SecondSide;
    }

    public void setSecondSide(String secondSide) {
        SecondSide = secondSide;
    }


    public String getWorkDate() {
        return WorkDate;
    }


    public void setWorkDate(String workDate) {

        WorkDate = workDate;

    }


    public String getLoanDate() {
        return LoanDate;
    }

    public void setLoanDate(String loanDate) {
        LoanDate = loanDate;

    }

    public String getInterestRate() {
        return InterestRate;
    }

    public void setInterestRate(String interestRate) {
        InterestRate = interestRate;
    }

    public String getDelayInterestRate() {
        return DelayInterestRate;
    }

    public void setDelayInterestRate(String delayInterestRate) {
        DelayInterestRate = delayInterestRate;
    }

    public String getClearanceNumber() {
        return ClearanceNumber;
    }

    public LoansType getLoanType() {
        return loanType;
    }

    public void setLoanType(LoansType loanType) {
        this.loanType = loanType;
    }

    public FinanceType getFinanceType() {
        return financeType;
    }

    public void setFinanceType(FinanceType financeType) {
        this.financeType = financeType;
    }

    public void setClearanceNumber(String clearanceNumber) {
        ClearanceNumber = clearanceNumber;
    }

    public String getTotalAmmount() {
        return TotalAmmount;
    }

    public void setTotalAmmount(String totalAmmount) {
        TotalAmmount = totalAmmount;
    }

    public String getTotalAmmountAsString() {
        return TotalAmmountAsString;
    }

    public void setTotalAmmountAsString(String totalAmmountAsString) {
        TotalAmmountAsString = totalAmmountAsString;
    }

    public String getNetAmmount() {
        return NetAmmount;
    }

    public void setNetAmmount(String netAmmount) {
        NetAmmount = netAmmount;
    }

    public String getNetAmmountAsString() {
        return NetAmmountAsString;
    }

    public void setNetAmmountAsString(String netAmmountAsString) {
        NetAmmountAsString = netAmmountAsString;
    }

    public String getNumberOfVoucherAsString() {
        return NumberOfVoucherAsString;
    }

    public void setNumberOfVoucherAsString(String numberOfVoucherAsString) {
        NumberOfVoucherAsString = numberOfVoucherAsString;
    }

    public String getNumberOfVoucher() {
        return NumberOfVoucher;
    }

    public void setNumberOfVoucher(String numberOfVoucher) {
        NumberOfVoucher = numberOfVoucher;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public Branches getBranche() {
		return branche;
	}

	public void setBranche(Branches branche) {
		this.branche = branche;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    

    public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	
	

	public String getLoanNumber() {
		return LoanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		LoanNumber = loanNumber;
	}

	public boolean accept(Visitor visitor) { return visitor.visit(this); }}
