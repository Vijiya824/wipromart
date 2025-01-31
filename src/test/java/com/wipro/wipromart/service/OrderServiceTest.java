package com.wipro.wipromart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.wipromart.entity.Customer;
import com.wipro.wipromart.entity.Order;
import com.wipro.wipromart.entity.OrderItem;
import com.wipro.wipromart.entity.Product;
import com.wipro.wipromart.exception.ResourceNotFoundException;
import com.wipro.wipromart.repository.OrderRepository;

@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @Test
    void testSaveOrder() {
        // Prepare test data
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        Product product = new Product();
        product.setProductId(101L);
        product.setProductPrice(100.0);

        OrderItem orderItem = new OrderItem();
        orderItem.setQty(2);
        orderItem.setProduct(product);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderItems(orderItems);

        when(customerService.getCustomerById(1L)).thenReturn(customer);
        when(productService.getProductById(101L)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method
        Order savedOrder = orderService.saveOrder(order);

        // Verify the results
        assertEquals(200.0, savedOrder.getOrderAmount());
        assertEquals("Success", savedOrder.getOrderStatus());
        assertEquals(1, savedOrder.getOrderItems().size());

        verify(customerService, times(1)).getCustomerById(1L);
        verify(productService, times(1)).getProductById(101L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrderDetails() {
        // Prepare test data
        Order order = new Order();
        order.setOrderId(1);
        order.setOrderDate(LocalDate.now());
        order.setOrderAmount(200.0);

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        // Call the method
        Order fetchedOrder = orderService.getOrderDetails(1);

        // Verify the results
        assertEquals(1, fetchedOrder.getOrderId());
        assertEquals(200.0, fetchedOrder.getOrderAmount());

        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testGetOrderDetailsWithException() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        // Verify exception is thrown
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderDetails(1));

        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllOrders() {
        // Prepare test data
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setOrderDate(LocalDate.now());
        order1.setOrderAmount(200.0);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setOrderDate(LocalDate.now());
        order2.setOrderAmount(300.0);

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        when(orderRepository.findAll()).thenReturn(orders);

        // Call the method
        List<Order> fetchedOrders = orderService.getAllOrders();

        // Verify the results
        assertEquals(2, fetchedOrders.size());
        assertEquals(200.0, fetchedOrders.get(0).getOrderAmount());
        assertEquals(300.0, fetchedOrders.get(1).getOrderAmount());

        verify(orderRepository, times(1)).findAll();
    }
}
