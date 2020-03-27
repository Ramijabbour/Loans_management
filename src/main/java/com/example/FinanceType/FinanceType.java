package com.example.FinanceType;

		import ValidContent_Visitor.Visitor;

		import javax.persistence.Entity;
		import javax.persistence.GeneratedValue;
		import javax.persistence.GenerationType;
		import javax.persistence.Id;

@Entity
public class FinanceType {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int FinanceTypeID ;
	public String TypeName="";
	public double FundintRate ;

	public String getFunded_propse() {
		return funded_propse;
	}

	public void setFunded_propse(String funded_propse) {
		this.funded_propse = funded_propse;
	}

	public String funded_propse;
	public String lenght;


	public FinanceType(String typeName,double fundingRatio,String len) {
		this.TypeName = typeName ;
		this.FundintRate = fundingRatio ;
		this.lenght = len  ;
	}

	public double getFundintRate() {
		return FundintRate;
	}

	public void setFundintRate(double fundintRate) {
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
	public boolean accept(Visitor visitor) { return visitor.visit(this); }}
