package com.cg.dao;
import java.util.List;

import com.cg.dto.Customer;
import com.cg.dto.Transactions;
import com.cg.exception.InvalidInputException;

public interface IPaytmWalletDao
{
	public boolean save(Customer customer) throws InvalidInputException;

	public Customer findOne(String mobileNo) throws InvalidInputException;

	public void startTransaction();
	
	public void commitTransaction();

	public void update(Customer customer);
	
	public List<Transactions> getAllTrasactions(String mobileNo);
	
	public boolean save(Transactions transaction) throws InvalidInputException;
}