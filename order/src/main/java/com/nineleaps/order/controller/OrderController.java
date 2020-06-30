package com.nineleaps.order.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.order.exception.OrderNotPlacedException;
import com.nineleaps.order.model.Order;
import com.nineleaps.order.model.Product;
import com.nineleaps.order.service.OrderService;
import com.nineleaps.order.service.talk.to.ProductProxy;

@RestController
public class OrderController {


@Autowired
private OrderService orderService;
	@Autowired
	KafkaTemplate<String, Object> template;
	@Autowired
	private ProductProxy proxy;

	private String topic = "nineleaps";

	@PostMapping(value = "/create",consumes = { "application/json", "application/xml" })
	public ResponseEntity<?> createOrder(@Valid @RequestBody Order order) {
		
		Product product = proxy.checkProductAvailability(order.getItem().getProductId());
		if (product != null) {
			template.send(topic, order);
			orderService.createOrder(order);
			return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		} else
			throw new OrderNotPlacedException("Product is out of stock");

	}
	
	@DeleteMapping(path="/delete/{id}")
	public void deleteById(@PathVariable int id) {
		orderService.deleteByOrderId(id);
	}
}
