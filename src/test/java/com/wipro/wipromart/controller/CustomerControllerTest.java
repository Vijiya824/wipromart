package com.wipro.wipromart.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wipro.wipromart.entity.Customer;
import com.wipro.wipromart.service.CustomerService;

@SpringBootTest
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(20);
        customer.setFirstName("ram");
        customer.setLastName("charan");
        customer.setEmail("ram@gmail.com");
        customer.setMobile("9182359795");
        customer.setCity("hyd");

        when(customerService.saveCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.addcustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("ram", response.getBody().getFirstName());
        assertEquals("9182359795", response.getBody().getMobile());
    }

    @Test
    void testFetchCustomerById() {
        Customer customer = new Customer();
        customer.setCustomerId(20);
        customer.setFirstName("ram");
        customer.setLastName("charan");
        customer.setEmail("ram@gmail.com");
        customer.setMobile("9182359795");
        customer.setCity("hyd");

        when(customerService.getCustomerById(20L)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.fetchcustomerById(20L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ram", response.getBody().getFirstName());
        assertEquals("hyd", response.getBody().getCity());
    }

    @Test
    void testFetchAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setCustomerId(20);
        customer1.setFirstName("ram");
        customer1.setLastName("charan");
        customer1.setEmail("ram@gmail.com");
        customer1.setMobile("9182359795");
        customer1.setCity("hyd");

        Customer customer2 = new Customer();
        customer2.setCustomerId(30);
        customer2.setFirstName("pawan");
        customer2.setLastName("kalyan");
        customer2.setEmail("pawan@gmail.com");
        customer2.setMobile("8734675632");
        customer2.setCity("goa");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        List<Customer> response = customerController.fetchAllcustomers();

        assertEquals(2, response.size());
        assertEquals("ram", response.get(0).getFirstName());
        assertEquals("pawan", response.get(1).getFirstName());
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setCustomerId(20);
        customer.setFirstName("ram");
        customer.setLastName("charan");
        customer.setEmail("ram@gmail.com");
        customer.setMobile("9182359795");
        customer.setCity("hyd");

        when(customerService.getCustomerById(20L)).thenReturn(customer);

        customerController.fetchcustomerById(20L);
        verify(customerService, times(1)).getCustomerById(20L);
    }
}