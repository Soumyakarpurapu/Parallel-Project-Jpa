package com.cg.dto;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class Transactions
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String mobileNo;
	private String transactionType;
	private String transactionStatus;
	private Date transactionDate;
	private Double amount;
	
	{
	transactionStatus = "failed";
	}

	
	public Transactions() {
		super();
	}
	
	

	public Transactions(int id, String mobileNo, String transactionType, String transactionStatus, Date transactionDate,
			Double amount) {
		super();
		this.id = id;
		this.mobileNo = mobileNo;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.transactionDate = transactionDate;
		this.amount = amount;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date date) {
		this.transactionDate = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transactions [id=" + id + ", mobileNo=" + mobileNo + ", transactionType=" + transactionType
				+ ", transactionStatus=" + transactionStatus + ", transactionDate=" + transactionDate + ", amount="
				+ amount + "]";
	}
	
}
