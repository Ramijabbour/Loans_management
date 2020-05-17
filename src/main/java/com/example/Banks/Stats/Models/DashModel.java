package com.example.Banks.Stats.Models;

public class DashModel {
	
	private int TotalBanksCount ; 
	private int TotalBranchesCount ;
	private int TotalClientsCount ;
	private int TotalSystemUsers ;
	private int OpenLoansCount ; 
	private int ClosedLoansCount ; 
	private int ResLoansCount ; 
	private int TotalLoansCount ;
	private long LoansValue ; 
	
	private long OpenLoansValue ; 
	private long ClosedLoansValue ; 
	private long ResLoansValue ; 
	
	
	public DashModel() {}

	

	public DashModel(int totalBanksCount, int totalBranchesCount, int totalClientsCount, int totalSystemUsers,
			int openLoansCount, int closedLoansCount, int resLoansCount, int totalLoansCount, long loansValue,
			long openLoansValue, long closedLoansValue, long resLoansValue) {
		super();
		TotalBanksCount = totalBanksCount;
		TotalBranchesCount = totalBranchesCount;
		TotalClientsCount = totalClientsCount;
		TotalSystemUsers = totalSystemUsers;
		OpenLoansCount = openLoansCount;
		ClosedLoansCount = closedLoansCount;
		ResLoansCount = resLoansCount;
		TotalLoansCount = totalLoansCount;
		LoansValue = loansValue;
		OpenLoansValue = openLoansValue;
		ClosedLoansValue = closedLoansValue;
		ResLoansValue = resLoansValue;
	}


	public int getTotalBanksCount() {
		return TotalBanksCount;
	}
	public void setTotalBanksCount(int totalBanksCount) {
		TotalBanksCount = totalBanksCount;
	}
	public int getTotalBranchesCount() {
		return TotalBranchesCount;
	}
	public void setTotalBranchesCount(int totalBranchesCount) {
		TotalBranchesCount = totalBranchesCount;
	}
	public int getTotalClientsCount() {
		return TotalClientsCount;
	}
	public void setTotalClientsCount(int totalClientsCount) {
		TotalClientsCount = totalClientsCount;
	}
	public int getTotalSystemUsers() {
		return TotalSystemUsers;
	}
	public void setTotalSystemUsers(int totalSystemUsers) {
		TotalSystemUsers = totalSystemUsers;
	}
	public int getOpenLoansCount() {
		return OpenLoansCount;
	}
	public void setOpenLoansCount(int openLoansCount) {
		OpenLoansCount = openLoansCount;
	}
	public int getClosedLoansCount() {
		return ClosedLoansCount;
	}
	public void setClosedLoansCount(int closedLoansCount) {
		ClosedLoansCount = closedLoansCount;
	}
	public int getResLoansCount() {
		return ResLoansCount;
	}

	public void setResLoansCount(int resLoansCount) {
		ResLoansCount = resLoansCount;
	}
	public int getTotalLoansCount() {
		return TotalLoansCount;
	}
	public void setTotalLoansCount(int totalLoansCount) {
		TotalLoansCount = totalLoansCount;
	}
	public long getLoansValue() {
		return LoansValue;
	}
	public void setLoansValue(long loansValue) {
		LoansValue = loansValue;
	}
	public long getOpenLoansValue() {
		return OpenLoansValue;
	}
	public void setOpenLoansValue(long openLoansValue) {
		OpenLoansValue = openLoansValue;
	}
	public long getClosedLoansValue() {
		return ClosedLoansValue;
	}
	public void setClosedLoansValue(long closedLoansValue) {
		ClosedLoansValue = closedLoansValue;
	}
	public long getResLoansValue() {
		return ResLoansValue;
	}
	public void setResLoansValue(long resLoansValue) {
		ResLoansValue = resLoansValue;
	} 
	
}
