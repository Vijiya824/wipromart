package com.wipro.wipromart.controller;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wipro.wipromart.entity.Product;
import com.wipro.wipromart.service.ProductService;

@SpringBootTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void testAddProduct() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Laptop");
        product.setProductPrice(55000.0);
        product.setCategory("Electronics");

        when(productService.saveProduct(product)).thenReturn(product);

        ResponseEntity<Product> response = productController.addProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getProductName());
        assertEquals(55000.0, response.getBody().getProductPrice());
    }

    @Test
    void testFetchProductById() {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Laptop");
        product.setProductPrice(55000.0);
        product.setCategory("Electronics");

        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.fetchProduct(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Laptop", response.getBody().getProductName());
        assertEquals("Electronics", response.getBody().getCategory());
    }

    @Test
    void testFetchAllProducts() {
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setProductName("Laptop");
        product1.setProductPrice(55000.0);
        product1.setCategory("Electronics");

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setProductName("Smartphone");
        product2.setProductPrice(25000.0);
        product2.setCategory("Electronics");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        when(productService.getAllProducts()).thenReturn(products);

        List<Product> response = productController.fetchAllProducts();

        assertEquals(2, response.size());
        assertEquals("Laptop", response.get(0).getProductName());
        assertEquals("Smartphone", response.get(1).getProductName());
    }

   
}