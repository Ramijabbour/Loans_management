package com.example.settelmets.Models;

public class CheckDisposableModel {
	
		private int CheckId;
	    private String FirstBankName;
	    private  String SecondBankName ; 
	    private  String FirstBranchName;
	    private  String FirstBranchCode;
	    private  String SecondBranchName;
	    private  String SecondBranchCode;
	    private double Amount;
	
	    public CheckDisposableModel() {}
	
		public CheckDisposableModel(int checkId, String firstBankName, String secondBankName, String firstBranchName,
				String firstBranchCode, String secondBranchName, String secondBranchCode, double amount) {
			super();
			CheckId = checkId;
			FirstBankName = firstBankName;
			SecondBankName = secondBankName;
			FirstBranchName = firstBranchName;
			FirstBranchCode = firstBranchCode;
			SecondBranchName = secondBranchName;
			SecondBranchCode = secondBranchCode;
			Amount = amount;
		}






		public int getCheckId() {
			return CheckId;
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

		public void setCheckId(int checkId) {
			CheckId = checkId;
		}	
		
	

		public String getFirstBankName() {
			return FirstBankName;
		}




		public void setFirstBankName(String firstBankName) {
			FirstBankName = firstBankName;
		}




		public String getSecondBankName() {
			return SecondBankName;
		}




		public void setSecondBankName(String secondBankName) {
			SecondBankName = secondBankName;
		}




		public void setFirstBranchName(String firstBranchName) {
			FirstBranchName = firstBranchName;
		}

		public void setFirstBranchCode(String firstBranchCode) {
			FirstBranchCode = firstBranchCode;
		}

		public void setSecondBranchName(String secondBranchName) {
			SecondBranchName = secondBranchName;
		}

		public void setSecondBranchCode(String secondBranchCode) {
			SecondBranchCode = secondBranchCode;
		}

		public void setAmount(double amount) {
			Amount = amount;
		}   
		
		
}
