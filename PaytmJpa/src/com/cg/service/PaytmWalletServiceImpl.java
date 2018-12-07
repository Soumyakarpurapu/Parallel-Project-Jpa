package com.cg.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.dao.IPaytmWalletDao;
import com.cg.dao.PaytmWalletDaoImpl;
import com.cg.dto.Customer;
import com.cg.dto.Transactions;
import com.cg.dto.Wallet;
import com.cg.exception.WalletBalanceException;
import com.cg.exception.InvalidInputException;

public class PaytmWalletServiceImpl implements IPaytmWalletService{

	private IPaytmWalletDao dao;
	Map<String, List<Transactions>> transactions;
	public PaytmWalletServiceImpl() {
		dao = new PaytmWalletDaoImpl();
		
		transactions = new HashMap<>();
	}

	public Customer createAccount(String name, String mobileNo, Double amount) throws InvalidInputException {
		
		if(isValid(mobileNo) && isValidName(name) && amount.compareTo(new Double(0)) > 0) {
			Wallet wallet = new Wallet();
			Customer customer = new Customer();
		
			wallet.setBalance(amount);
			customer.setName(name);
			customer.setMobileNo(mobileNo);
			customer.setWallet(wallet);
			
			dao.startTransaction();
			dao.save(customer);
			dao.commitTransaction();
			if(dao.save(customer) == true) {
				transactions.put(mobileNo, null);
				return customer;
			}
			else
				throw new InvalidInputException("User already present");
		}
			
		
		else throw new InvalidInputException("Enter Valid details");

	}

	public Customer showBalance(String mobileNo) throws InvalidInputException {
		
		dao.startTransaction();
		Customer customer=dao.findOne(mobileNo);
		dao.commitTransaction();
		
		if(customer!=null)
			return customer;
		else
			throw new InvalidInputException("Invalid mobile no ");
	}

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, Double amount) throws InvalidInputException, WalletBalanceException {
		
		if(isValid(sourceMobileNo) == false || isValid(targetMobileNo) == false) 
			throw new InvalidInputException();
		
		Customer customer = withdrawAmount(sourceMobileNo, amount);
		depositAmount(targetMobileNo, amount);
		
		return customer;
	}

	public Customer depositAmount(String mobileNo, Double amount) throws InvalidInputException 
	{
		if(amount.compareTo(new Double(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValid(mobileNo)) {
			Customer customer = dao.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			
			wallet.setBalance(wallet.getBalance()+(amount));
			Transactions transactions = new Transactions();
			transactions.setAmount(amount);
			transactions.setMobileNo(mobileNo);
			transactions.setTransactionType("deposit");
			transactions.setTransactionStatus("success");
			transactions.setTransactionDate(new Date());
			dao.save(transactions);
			
			dao.startTransaction();
			dao.update(customer);
			dao.commitTransaction();
			
			return customer;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}

	public Customer withdrawAmount(String mobileNo, Double amount) throws InvalidInputException, WalletBalanceException 
	{	
		if(amount.compareTo(new Double(0)) <= 0) 
			throw new InvalidInputException("Enter valid amount");
		
		if(isValid(mobileNo)) 
		{
			Customer customer = dao.findOne(mobileNo);
			Wallet wallet = customer.getWallet();
			
			if(amount.compareTo(wallet.getBalance()) > 0) 
				throw new WalletBalanceException("Amount is not sufficient in your account");
			
			wallet.setBalance(wallet.getBalance()-amount);
			customer.setWallet(wallet);
			
			Transactions transactions = new Transactions();
			transactions.setAmount(amount);
			transactions.setMobileNo(mobileNo);
			transactions.setTransactionType("withdraw");
			transactions.setTransactionStatus("success");
			transactions.setTransactionDate(new Date());
			dao.save(transactions);
			
			dao.startTransaction();
			dao.update(customer);
			dao.commitTransaction();
			
			return customer;
		}
		else throw new InvalidInputException("Enter valid mobile number");
	}

	@Override
	public List<Transactions> getAllTrasactions(String mobileNo) {
		return dao.getAllTrasactions(mobileNo);
		
	}

	public boolean isValid(String mobileNo) {
		if(mobileNo.matches("[1-9][0-9]{9}")) 
		{
			return true;
		}		
		else 
			return false;
	}
	
	private boolean isValidName(String name) {
		if( name != null && name.matches("[A-Z]{1}[a-z]{3,10}"))
			return true;
		else return false;
	}
}