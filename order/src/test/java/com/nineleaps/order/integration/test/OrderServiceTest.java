package com.nineleaps.order;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Date;

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
import com.nineleaps.order.controller.OrderController;
import com.nineleaps.order.model.Address;
import com.nineleaps.order.model.Item;
import com.nineleaps.order.model.Order;
import com.nineleaps.order.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ComponentScan
@ContextConfiguration
@SpringBootTest({ "cassandra.contactpoints=127.0.0.1", "cassandra.port=9042", "cassandra.keyspace=nineleaps" })
@CassandraUnit
public class OrderServiceTest {

	private MockMvc mockMvc;
	
	@Autowired
	private OrderController orderController;
	@Mock
	private OrderService orderService;
	
	@Before
	public void init() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}
	
	
	@Test
	public void createOrderTest() throws Exception {
		int num =10;
		while(num > 0) {
			try {
		Address address=new Address("1002","Nirala","Noida","U.P","India",201308);
		Item item = new Item(1,1,new BigDecimal(100.00));
		Order order = new Order (1,new Date(29-06-2020),"Vivek","vivek@gmail.com",address,item,1);
		when(orderService.isExist(1)).thenReturn(false);
		doNothing().when(orderService).createOrder(order);
		
		mockMvc.perform(post("/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(order)))
		.andExpect(status().isCreated());
		
		verifyNoMoreInteractions(orderService);
			}catch(Exception ex) {}
		num--;
		}
		
	}
	
	@Test
	public void deleteByOrderIdTest() throws Exception {
		
		Address address=new Address("1002","Nirala","Noida","U.P","India",201308);
		Item item = new Item(1,1,new BigDecimal(100.00));
		Order order = new Order (2,new Date(29-06-2020),"Vivek","vivek@gmail.com",address,item,1);
		
		doNothing().when(orderService).deleteByOrderId(order.getId());
		mockMvc.perform(delete("/delete/{id}", order.getId())).andExpect(status().isOk());
		verifyNoMoreInteractions(orderService);
		
	}
	private static String asJsonString(final Object obj) {
		try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}
}
