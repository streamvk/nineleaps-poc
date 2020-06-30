package com.nineleaps.product;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.cassandraunit.spring.CassandraUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nineleaps.product.controller.ProductController;
import com.nineleaps.product.model.Product;
import com.nineleaps.product.model.ProductPrimaryKey;
import com.nineleaps.product.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ComponentScan
@ContextConfiguration
@SpringBootTest({ "cassandra.contactpoints=127.0.0.1", "cassandra.port=9042", "cassandra.keyspace=nineleaps" })
@CassandraUnit
public class ProductServiceTest {

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;
	@Autowired
	private ProductController productController;

	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void createProductTest() throws Exception {

		Product product = new Product(new ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly.");
		when(productService.isProductAvailable(1)).thenReturn(false);
		doNothing().when(productService).saveProduct(product);

		mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(product)))
				.andExpect(status().isCreated());

		verifyNoMoreInteractions(productService);
	}

	@Test
	public void findProductByPkIdTest() throws Exception {

		Product product = new Product(new ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly.");
		when(productService.findProductByPkId(1)).thenReturn(product);

		mockMvc.perform(get("/get/{id}", 1)).andExpect(status().isFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.pk.id", is(1))).andExpect(jsonPath("$.name", is("iPhone")));
		verifyNoMoreInteractions(productService);

	}

	@Test
	public void findAllProductTest() throws Exception {

		List<Product> list = new ArrayList<>();
		list.add(new Product(new ProductPrimaryKey(1, 1), "iPhone", 100000.00D, "iPhones are very constly."));

		when(productService.findAllProduct()).thenReturn(list);

		mockMvc.perform(get("/all")).andExpect(status().isFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[0].pk.id", is(1))).andExpect(jsonPath("$.[0].name", is("iPhone")));
		verifyNoMoreInteractions(productService);

	}
	
	@Test
	public void deleteProductByIdTest() throws Exception {
		Product product = new Product(new ProductPrimaryKey(1, 1), "iphone", 100000.00D, "");
		when(productService.findProductByPkId(product.getPk().getId())).thenReturn(product);
		doNothing().when(productService).deleteProduct(product);
		mockMvc.perform(delete("/delete/{id}", product.getPk().getId())).andExpect(status().isOk());
		verifyNoMoreInteractions(productService);
		
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
