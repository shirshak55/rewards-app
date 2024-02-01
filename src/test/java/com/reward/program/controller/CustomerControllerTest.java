package com.reward.program.controller;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.reward.program.model.Customer;
import com.reward.program.model.Purchase;
import com.reward.program.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private CustomerController customerController;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void createCustomersTest() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "customerOne"));
		customers.add(new Customer(2L, "customerTwo"));

		when(customerRepository.saveAll(customers)).thenReturn(customers);

		List<Customer> result = customerController.createCustomers(customers);
		System.out.println("Result to String: " + result);
		assertNotNull(result);
		System.out.println(result);
		assertEquals(2, result.size());
	}

	@Test
	public void createPurchasesTest() {
		Long customerId = 1L;
		List<Purchase> purchases = new ArrayList<>();
		purchases.add(new Purchase(1L, BigDecimal.valueOf(100), Instant.now()));
		purchases.add(new Purchase(2L, BigDecimal.valueOf(200), Instant.now()));

		Customer customer = new Customer(customerId, "customer");
		// customer.setPurchases(purchases);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);

		ResponseEntity<Customer> result = customerController.createPurchases(customerId, purchases);
		assertNotNull(result);
		assertEquals(2, Objects.requireNonNull(result.getBody()).getPurchases().size());
	}

	@Test
	public void getAllCustomersTest() {
		List<Customer> customers = new ArrayList<>();
		customers.add(new Customer(1L, "CustomerOne"));
		customers.add(new Customer(2L, "Customer"));

		when(customerRepository.findAll()).thenReturn(customers);

		List<Customer> result = customerController.getAllCustomers();
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	public void createPurchases_whenCustomerNotFound_throwException() {
		Long customerId = 1L;
		List<Purchase> purchases = new ArrayList<>();
		purchases.add(new Purchase(1L, BigDecimal.valueOf(100), Instant.now()));

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
		assertThrows(Exception.class, ()-> customerController.createPurchases(customerId, purchases));
	}

	@Test
	public void createPurchases_whenCustomerFound_savesPurchasesForCustomer() {
		Long customerId = 1L;
		List<Purchase> purchases = new ArrayList<>();
		purchases.add(new Purchase(1L, BigDecimal.valueOf(100), Instant.now()));
		Customer customer = new Customer();

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);

		customerController.createPurchases(customerId, purchases);

		assertEquals(1, customer.getPurchases().size());
		assertEquals(purchases, customer.getPurchases());
		Mockito.verify(customerRepository).findById(customerId);
		Mockito.verify(customerRepository).save(customer);
	}

	@Test
	public void createPurchases_whenCustomerFound_returnCreated() {
		Long customerId = 1L;
		List<Purchase> purchases = new ArrayList<>();
		purchases.add(new Purchase());

		Customer customer = new Customer();
		customer.setId(customerId);

		when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
		when(customerRepository.save(customer)).thenReturn(customer);

		ResponseEntity<Customer> response = customerController.createPurchases(customerId, purchases);

		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals(customer, response.getBody());
		Mockito.verify(customerRepository).findById(customerId);
		Mockito.verify(customerRepository).save(customer);
	}

	@Test
	public void createPurchases_whenCustomerNotFound_returnNotFound() {
		Long customerId = 1L;
		List<Purchase> purchases = new ArrayList<>();
		purchases.add(new Purchase());

		when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

		ResponseEntity<Customer> response = customerController.createPurchases(customerId, purchases);

		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assert.assertNull(response.getBody());
		Mockito.verify(customerRepository).findById(customerId);
		Mockito.verify(customerRepository, never()).save(any());
	}

}
