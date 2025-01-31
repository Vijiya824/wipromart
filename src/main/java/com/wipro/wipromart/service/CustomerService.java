package com.wipro.wipromart.service;

import java.util.List;

import com.wipro.wipromart.entity.Customer;
import com.wipro.wipromart.entity.Product;
import com.wipro.wipromart.model.CustomerDto;


public interface CustomerService {

    CustomerDto saveCustomer(CustomerDto customerDto);
	
	CustomerDto getCustomerById(long customerId);
	
	List<CustomerDto> getAllCustomers();
	
	Customer updateCustomer(Customer customer);
	
	void deleteCustomer(long customerId);

}
