package com.wipro.wipromart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.wipromart.entity.Product;
import com.wipro.wipromart.exception.ResourceNotFoundException;
import com.wipro.wipromart.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService = new ProductServiceImpl();
    
    @Mock
    private ProductRepository productRepository;
    
    @Test
    void testGetProductByID() {
        // Arrange
        Product product = new Product();
        product.setProductId(200);
        product.setProductName("My Product");
        product.setProductPrice(5000);
        product.setMfd(LocalDate.of(2024, 10, 10));
        product.setCategory("dummy");

        Optional<Product> optionalProduct = Optional.of(product);
        
        // Mocking repository behavior
        when(productRepository.findById(200L)).thenReturn(optionalProduct);
        
        // Act
        Product actualProduct = productService.getProductById(200);

        // Assert
        assertEquals("My Product", actualProduct.getProductName(), "Product name does not match");
        assertEquals(5000, actualProduct.getProductPrice(), "Product price does not match");
        assertEquals("dummy", actualProduct.getCategory(), "Product category does not match");
    }
    
    @Test
    void testGetProductByIDWithException() {
        // Arrange
        when(productRepository.findById(200L)).thenReturn(Optional.empty());
        
        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(200), 
                     "Expected ResourceNotFoundException when product not found");
    }

    @Test
    void testSaveProduct() {
        // Arrange
        Product product = new Product();
        product.setProductId(200);
        product.setProductName("My Product");
        product.setProductPrice(5000);
        product.setMfd(LocalDate.of(2024, 10, 10));
        product.setCategory("dummy");
        
        // Mocking repository behavior
        when(productRepository.save(product)).thenReturn(product);
        
        // Act
        Product newProduct = productService.saveProduct(product);
        
        // Assert
        assertEquals(200, newProduct.getProductId(), "Product ID does not match");
        assertEquals("My Product", newProduct.getProductName(), "Product name does not match");
        assertEquals(5000, newProduct.getProductPrice(), "Product price does not match");
        assertEquals("dummy", newProduct.getCategory(), "Product category does not match");
        assertEquals(LocalDate.of(2024, 10, 10), newProduct.getMfd(), "Manufacture date does not match");
    }
    
    @Test
    void testGetAllProducts() {
        // Arrange
        Product product1 = new Product();
        product1.setProductId(200);
        product1.setProductName("Product 1");
        product1.setProductPrice(5000);
        product1.setMfd(LocalDate.of(2024, 10, 10));
        product1.setCategory("Category 1");

        Product product2 = new Product();
        product2.setProductId(201);
        product2.setProductName("Product 2");
        product2.setProductPrice(7000);
        product2.setMfd(LocalDate.of(2024, 11, 11));
        product2.setCategory("Category 2");

        Product product3 = new Product();
        product3.setProductId(202);
        product3.setProductName("Product 3");
        product3.setProductPrice(9000);
        product3.setMfd(LocalDate.of(2024, 12, 12));
        product3.setCategory("Category 3");

        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(product1);
        mockProducts.add(product2);
        mockProducts.add(product3);

        // Mocking repository behavior
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        assertEquals(3, actualProducts.size(), "Number of products does not match");
        assertEquals("Product 1", actualProducts.get(0).getProductName(), "First product name does not match");
        assertEquals("Category 2", actualProducts.get(1).getCategory(), "Second product category does not match");
        assertEquals(9000, actualProducts.get(2).getProductPrice(), "Third product price does not match");
    }
    
    @Test
    void testDeleteProduct() {
    	
    	Product product = new Product();
        product.setProductId(200);
        product.setProductName("My Product");
        product.setProductPrice(5000);
        product.setMfd(LocalDate.of(2024, 10, 10));
        product.setCategory("dummy");
     
        Optional<Product> optionalProduct = Optional.of(product);
        
        when(productRepository.findById(200L)).thenReturn(optionalProduct);
        doNothing().when(productRepository).delete(product);

       
        productService.deleteProduct(200);
        
        verify(productRepository,times(1)).findById(200L);
        verify(productRepository,times(1)).delete(product);
    }
}