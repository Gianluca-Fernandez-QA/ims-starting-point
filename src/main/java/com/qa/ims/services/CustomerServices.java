package com.qa.ims.services;

import java.util.List;

import com.qa.ims.persistence.dao.Dao;
import com.qa.ims.persistence.domain.Customer;

public class CustomerServices implements CrudServices<Customer> {

	private Dao<Customer> customerDao;

	public CustomerServices(Dao<Customer> customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public List<Customer> readAll() {
		return customerDao.readAll();
	}

	@Override
	public Customer create(Customer customer) {
		return customerDao.create(customer);
	}

	@Override
	public Customer update(Customer customer) {
		return customerDao.update(customer);
	}

	@Override
	public void delete(Long id) {
		customerDao.delete(id);
	}

}
