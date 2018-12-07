package com.cg.dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Wallet {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "wallet_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int walletId;
	
	public int getWalletId() {
		return walletId;
	}

	public void setWalletId(int walletId) {
		this.walletId = walletId;
	}

	public Wallet(int walletId, Double balance) {
		super();
		this.walletId = walletId;
		this.balance = balance;
	}

	private Double balance;

	
	public Wallet(Double amount) {
		this.balance=amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return ", balance="+balance;
	}
	public Wallet() {
		super();
	}

}
