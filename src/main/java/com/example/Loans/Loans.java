package com.example.Loans;


import javax.persistence.*;


import ValidContent_Visitor.Visitor;
import com.example.Banks.Banks;
import com.example.Clients.Clients;
import com.example.FinanceType.FinanceType;
import com.example.LoansType.LoansType;
import com.example.security.user.User;
import org.springframework.format.annotation.DateTimeFormat;

//import sun.util.calendar.BaseCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity

public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int LoanID;

    public String FirstSide;

    public String SecondSide;


    public String WorkDate;


    public String LoanDate;


    public String InterestRate;

    public String DelayInterestRate;


    public String ClearanceNumber;

    public String TotalAmmount;

    public String TotalAmmountAsString;

    public String NetAmmount;


    private String NetAmmountAsString;

    private String NumberOfVoucherAsString;

    private String NumberOfVoucher;

    private String purpose;

    @ManyToOne
    private Clients client = null;

    @ManyToOne
    private Banks bank = null;

    @ManyToOne
    private User user = null;

    @ManyToOne
    private LoansType loanType = null;

    @ManyToOne
    private FinanceType financeType = null;

    public int getLoanID() {
        return LoanID;
    }

    public void setLoanID(int loanID) {
        LoanID = loanID;
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

    public Banks getBank() {
        return bank;
    }

    public void setBank(Banks bank) {
        this.bank = bank;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean accept(Visitor visitor) { return visitor.visit(this); }}
