package com.nineleaps.supplier;

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
import com.nineleaps.supplier.controller.SupplierController;
import com.nineleaps.supplier.model.Supplier;
import com.nineleaps.supplier.service.SupplierService;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ComponentScan
@ContextConfiguration
@SpringBootTest({ "cassandra.contactpoints=127.0.0.1", "cassandra.port=9042", "cassandra.keyspace=nineleaps" })
@CassandraUnit
public class SupplierServiceTest {

	private MockMvc mockMvc;
	@Autowired
	private SupplierController supplierController;
	@Mock
	private SupplierService supplierService;


	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();
	}

	@Test
	public void createSupplierTest() throws Exception {

		Supplier supplier = new Supplier(1, "Gal Gadot", "galgadot@gmail.com");
		when(supplierService.isSupplierExists(1)).thenReturn(false);
		doNothing().when(supplierService).saveSupplier(supplier);

		mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(supplier)))
				.andExpect(status().isCreated());
				//.andExpect(header().string("location", containsString("http://localhost/create")));

		verifyNoMoreInteractions(supplierService);
	}

	@Test
	public void findSupplierByIdTest() throws Exception {

		Supplier supplier = new Supplier(1, "Gal Gadot", "galgadot@gmail.com");
		when(supplierService.findSupplierById(1)).thenReturn(supplier);
		mockMvc.perform(get("/get/v2/{id}", 1)).andExpect(status().isFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("Gal Gadot")));
		verifyNoMoreInteractions(supplierService);
	}

	@Test
	public void getAllSupplierTest() throws Exception {

		List<Supplier> list = new ArrayList<>();
		list.add(new Supplier(1, "Gal Gadot", "galgadot@gmail.com"));
		when(supplierService.getAllSupplier()).thenReturn(list);
		mockMvc.perform(get("/all")).andExpect(status().isFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[1].id", is(1))).andExpect(jsonPath("$.[1].name", is("Gal Gadot")));
		verifyNoMoreInteractions(supplierService);
	}

	@Test
	public void deleteSupplierByIdTest() throws Exception {
		Supplier supplier = new Supplier(1, "Gal Gadot", "galgadot@gmail.com");
		when(supplierService.findSupplierById(supplier.getId())).thenReturn(supplier);
		doNothing().when(supplierService).deleteSupplierById(supplier.getId());
		mockMvc.perform(delete("/delete/{id}", supplier.getId())).andExpect(status().isOk());
		verifyNoMoreInteractions(supplierService);
	}
	
	private static String asJsonString(final Object obj) {
		try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

}
