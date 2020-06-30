package com.nineleaps.order.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.order.model.Order;
import com.nineleaps.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	public void createOrder(Order order) {
		repository.save(order);
	}
	
	public void deleteByOrderId(int id) {
		repository.deleteById(id);
	}

	public void deleteOrder(Order order){
		repository.delete(order);
	}
	public boolean isExist(int id) {
		Optional<Order> order = repository.findById(id);
		if (order.isPresent())
			return true;
		else
			return false;
	}

}
