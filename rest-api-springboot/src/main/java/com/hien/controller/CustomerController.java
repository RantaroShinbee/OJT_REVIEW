package com.hien.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hien.entity.Customer;
import com.hien.service.CustomerService;

@RestController
@RequestMapping(value = "api/v1/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping(value = { "", "/" })
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customer = customerService.findAllByOrderByCreateAt();
		if (customer.isEmpty()) {
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{offset}/{limit}")
	public ResponseEntity<List<Customer>> getAllCustomersPageable(@PathVariable int limit, @PathVariable int offset) {
		List<Customer> customer = customerService.findAllByOrderByCreateAt(limit, offset);
		if (customer.isEmpty()) {
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customer, HttpStatus.OK);
	}

	@GetMapping(value = "/{_id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable String _id) {
		Customer customer = customerService.findBy_id(_id);
		if (customer == null) {
			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
		if (customerService.save(customer) != null) {
			return new ResponseEntity<String>("Created !", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Customer Existed!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{_id}")
	public ResponseEntity<Void> deleteCustomerById(@PathVariable String _id) {
		if (customerService.findBy_id(_id) == null) {
			System.out.println("A Customer with id " + _id + " dont exist");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		customerService.deleteBy_id(_id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/{_id}")
	public ResponseEntity<Void> updateUserById(@RequestBody Customer customer, @PathVariable String _id) {
		if (customerService.findBy_id(_id) == null) {
			System.out.println("A Customer with name " + customer.getName() + " dont exist");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		customerService.update(customer);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
