package com.reward.program.controller;

import java.util.List;
import java.util.Optional;

import com.reward.program.model.Customer;
import com.reward.program.model.Purchase;
import com.reward.program.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	@PostMapping
	public List<Customer> createCustomers(@RequestBody List<Customer> customers) {
		return this.customerRepository.saveAll(customers);
	}

	@PostMapping("/{customerId}/purchases")
	public ResponseEntity<Customer> createPurchases(@PathVariable Long customerId,
			@RequestBody List<Purchase> purchases) {
		Optional<Customer> customer = this.customerRepository.findById(customerId);
        customer.ifPresent(value -> purchases.forEach(value::addToPurchases));

		return new ResponseEntity<Customer>(this.customerRepository.save(customer.get()), HttpStatus.CREATED);
	}

	@GetMapping
	public List<Customer> getAllCustomers() {
		return this.customerRepository.findAll();
	}
}
