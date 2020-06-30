package com.nineleaps.order.junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.nineleaps.order.model.Address;
import com.nineleaps.order.model.Item;
import com.nineleaps.order.model.Order;
import com.nineleaps.order.repository.OrderRepository;
import com.nineleaps.order.service.OrderService;

public class OrderServiceJunitTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getOrderById() {

		Address address = new Address("1002", "Nirala", "Noida", "U.P", "India", 201308);
		Item item = new Item(1, 1, new BigDecimal(100.00));
		Order order = new Order(1, new Date(29 - 06 - 2020), "Vivek", "vivek@gmail.com", address, item, 1);

		Optional<Order> orderOp = Optional.of(order);
		when(orderRepository.findById(1)).thenReturn(orderOp);

		assertEquals("Vivek", orderOp.get().getCustomerName());

	}

	@Test
	public void getAllOrderTest() {

		Address address = new Address("1002", "Nirala", "Noida", "U.P", "India", 201308);
		Item item = new Item(1, 1, new BigDecimal(100.00));
		Order order = new Order(1, new Date(29 - 06 - 2020), "Vivek", "vivek@gmail.com", address, item, 1);

		List<Order> orderList = new ArrayList<Order>();
		orderList.add(order);
		when(orderRepository.findAll()).thenReturn(orderList);
		assertEquals(1, orderList.size());
	}

	@Test
	public void deleteOrderById() {
		orderService.deleteByOrderId(1);
		verify(orderRepository, timeout(1)).deleteById(1);
	}

	@Test
	public void deleteOrder() {
		Address address = new Address("1002", "Nirala", "Noida", "U.P", "India", 201308);
		Item item = new Item(1, 1, new BigDecimal(100.00));
		Order order = new Order(1, new Date(29 - 06 - 2020), "Vivek", "vivek@gmail.com", address, item, 1);

		orderService.deleteOrder(order);
		verify(orderRepository, timeout(1)).delete(order);
	}
}
