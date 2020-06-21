package com.example.settelmets.Models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.SiteConfig.MasterService;
import com.example.aspect.EncryptDecrypt.IntEncryptDecryptConverter;
import com.example.aspect.EncryptDecrypt.StringEncryptDecryptConverter;
import com.example.settelmets.RTGSLink.SettlementReportModel;

@Entity
public class SettledChaque implements Serializable{

		private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	@Convert(converter = IntEncryptDecryptConverter.class)
	 	private int id;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String FirstBankName;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String FirstBranchName;
	 
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String FirstBranchCode;	 	
	 	
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String SecondBankName;
	 	
	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String SecondBranchName;

	 	@Column(nullable = false )
	 	@Convert(converter = StringEncryptDecryptConverter.class)
	 	private String SecondBranchCode;
	 	
	 	@Column(nullable = false )
	 	//@Convert(converter = DoubleEncryptDecryptConverter.class)
	 	private long Amount;

	 	@Column(nullable = false )
	 	private String localDateTime ; 
	 	
	 	private boolean sent = false ; 
	 	
	 	@ManyToOne
	 	private SettlementReportModel settlementReportModel = null ; 
	 
	    public SettledChaque() {

	    }
	    
		public SettledChaque(String firstBankName, String firstBranchName, String firstBranchCode,
				String secondBankName, String secondBranchName, String secondBranchCode, long amount) {
			super();
			FirstBankName = firstBankName;
			FirstBranchName = firstBranchName;
			FirstBranchCode = firstBranchCode;
			SecondBankName = secondBankName;
			SecondBranchName = secondBranchName;
			SecondBranchCode = secondBranchCode;
			Amount = amount;
			this.localDateTime = MasterService.getDateAsString() ;
		}




		public int getId() {
			return id;
		}

		public String getFirstBankName() {
			return FirstBankName;
		}

		public String getFirstBranchName() {
			return FirstBranchName;
		}

		public String getFirstBranchCode() {
			return FirstBranchCode;
		}

		public String getSecondBankName() {
			return SecondBankName;
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

		public String getLocalDateTime() {
			return localDateTime;
		}

		public SettlementReportModel getSettlementReportModel() {
			return settlementReportModel;
		}

		public void setSettlementReportModel(SettlementReportModel settlementReportModel) {
			this.settlementReportModel = settlementReportModel;
		}

		public void setFirstBankName(String firstBankName) {
			FirstBankName = firstBankName;
		}

		public void setFirstBranchName(String firstBranchName) {
			FirstBranchName = firstBranchName;
		}

		public void setFirstBranchCode(String firstBranchCode) {
			FirstBranchCode = firstBranchCode;
		}

		public void setSecondBankName(String secondBankName) {
			SecondBankName = secondBankName;
		}

		public void setSecondBranchName(String secondBranchName) {
			SecondBranchName = secondBranchName;
		}

		public void setSecondBranchCode(String secondBranchCode) {
			SecondBranchCode = secondBranchCode;
		}

		public void setAmount(long amount) {
			Amount = amount;
		}

		public void setLocalDateTime(String localDateTime) {
			this.localDateTime = localDateTime;
		}

		public boolean isSent() {
			return sent;
		}

		public void setSent(boolean sent) {
			this.sent = sent;
		}
	
}
