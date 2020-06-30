package com.nineleaps.product.junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nineleaps.product.model.Product;
import com.nineleaps.product.model.ProductPrimaryKey;
import com.nineleaps.product.repository.ProductRepository;
import com.nineleaps.product.service.ProductService;

public class ProductServiceJunitTest {
	
	@Mock
	private ProductRepository productRepository;
	
	@InjectMocks
	private ProductService productService;
	
	@Before 
	public  void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/*
	 * @Test public void createProductTest() { Product product = new Product(new
	 * ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly.");
	 * productService.saveProduct(product);
	 * verify(productRepository,times(1)).save(product);
	 * 
	 * }
	 */
	
	@Test
	public void findProductByIdTest() {
		
		Product product = new Product(new ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly.");
		
		when(productRepository.findByPkId(1)).thenReturn(product);
		
		Product prod= productService.findProductByPkId(1);
		assertEquals("iPhone", prod.getName());
	}
	
	@Test
	public void findAllProductTest() throws Exception {
		
		List<Product> prodList= new ArrayList<>();
		prodList.add(new Product(new ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly."));
		when(productRepository.findAll()).thenReturn(prodList);
		
		List<Product> list= productService.findAllProduct();
		assertEquals("iPhone", list.get(0).getName());
	}
	
	
	@Test
	public void deleteProduct() {
		
		Product product = new Product(new ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly.");
		
		productService.deleteProduct(product);
		verify(productRepository,times(1)).delete(product);
	}

}
