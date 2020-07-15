package com.example.WareHouseModel;

import javax.persistence.*;


//import sun.util.calendar.BaseCalendar;


@Entity
public class Fact_Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    
    public String TotalAmmount;
    
    public String NetAmmount;

    private String status; 

    @ManyToOne
    private Client_Dim client = null;

    @ManyToOne
    private Branch_Bank_Dim branche = null;

    @ManyToOne
    private User_Dim user = null;

    @ManyToOne
    private LoansType_Dim loanType = null;

    @ManyToOne
    private FinanceType_Dim financeType = null;

    @ManyToOne
    private Time_Dim time_Dim= null;

    
	public Fact_Table() {}

	public Fact_Table(String totalAmmount, String netAmmount, String status, Client_Dim client, Branch_Bank_Dim branche,
			User_Dim user, LoansType_Dim loanType, FinanceType_Dim financeType,Time_Dim t) {
		super();
		TotalAmmount = totalAmmount;
		NetAmmount = netAmmount;
		this.status = status;
		this.client = client;
		this.branche = branche;
		this.user = user;
		this.loanType = loanType;
		this.financeType = financeType;
		this.time_Dim=t;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTotalAmmount() {
		return TotalAmmount;
	}

	public void setTotalAmmount(String totalAmmount) {
		TotalAmmount = totalAmmount;
	}

	public String getNetAmmount() {
		return NetAmmount;
	}

	public void setNetAmmount(String netAmmount) {
		NetAmmount = netAmmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Client_Dim getClient() {
		return client;
	}

	public void setClient(Client_Dim client) {
		this.client = client;
	}

	public Branch_Bank_Dim getBranche() {
		return branche;
	}

	public void setBranche(Branch_Bank_Dim branche) {
		this.branche = branche;
	}

	public User_Dim getUser() {
		return user;
	}

	public void setUser(User_Dim user) {
		this.user = user;
	}

	public LoansType_Dim getLoanType() {
		return loanType;
	}

	public void setLoanType(LoansType_Dim loanType) {
		this.loanType = loanType;
	}

	public FinanceType_Dim getFinanceType() {
		return financeType;
	}

	public void setFinanceType(FinanceType_Dim financeType) {
		this.financeType = financeType;
	}
    
    
    


}
