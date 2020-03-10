package com.example.FinanceType;

		import javax.persistence.Entity;
		import javax.persistence.GeneratedValue;
		import javax.persistence.GenerationType;
		import javax.persistence.Id;

@Entity
public class FinanceType {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int FinanceTypeID ;
	private String TypeName="";
	private String FundintRate ;

	public String getFunded_propse() {
		return funded_propse;
	}

	public void setFunded_propse(String funded_propse) {
		this.funded_propse = funded_propse;
	}

	private String funded_propse;
	private String lenght;


	public FinanceType(String typeName,String fundingRatio,String len) {
		this.TypeName = typeName ;
		this.FundintRate = fundingRatio ;
		this.lenght = len  ;
	}

	public String getFundintRate() {
		return FundintRate;
	}

	public void setFundintRate(String fundintRate) {
		FundintRate = fundintRate;
	}

	public int getFinanceTypeID() {
		return FinanceTypeID;
	}
	public void setFinanceTypeID(int financeTypeID) {
		FinanceTypeID = financeTypeID;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}


	public void setLenght(String len) {
		this.lenght = len ;
	}
	public String getLenght() {
		return this.lenght ;
	}
}
