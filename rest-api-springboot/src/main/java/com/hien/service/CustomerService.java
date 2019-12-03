package com.hien.service;

import java.util.List;

import com.hien.entity.Customer;

public interface CustomerService {
	List<Customer> findAllByOrderByCreateAt();

	Customer findBy_id(String _id);

	Customer save(Customer customer);
	
	void deleteBy_id(String _id);

	List<Customer> findAllByOrderByCreateAt(int limit, int offset);

	void update(Customer customer);
}
