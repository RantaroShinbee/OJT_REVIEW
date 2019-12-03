package com.hien.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hien.entity.Customer;
import com.hien.repository.CustomerRepository;
import com.hien.repository.CustomerRepositoryCustom;
import com.hien.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CustomerRepositoryCustom customerRepositoryCustom;
	
	@Override
	public List<Customer> findAllByOrderByCreateAt() {
		return customerRepository.findAllByOrderByCreateAt();
	}

	@Override
	public Customer findBy_id(String _id) {
		return customerRepository.findBy_id(_id);
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void deleteBy_id(String _id) {
		customerRepository.deleteBy_id(_id);
	}

	@Override
	public List<Customer> findAllByOrderByCreateAt(int limit, int offset) {
		return customerRepository.findAllByOrderByCreateAt(PageRequest.of(offset, limit));
	}

	@Override
	public void update(Customer customer) {
		customerRepositoryCustom.update(customer);
	}

}
