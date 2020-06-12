package com.nineleaps.order.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.order.exception.OrderNotPlacedException;
import com.nineleaps.order.model.Order;
import com.nineleaps.order.model.Product;
import com.nineleaps.order.repository.OrderRepository;
import com.nineleaps.order.service.talk.to.ProductProxy;

@RestController
public class OrderController {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private ProductProxy proxy;
	@Autowired
	KafkaTemplate<String, Object> template;

	private String topic = "nineleaps";

	@PostMapping(value = "/create")
	public void createOrder(@Valid @RequestBody Order order) {
		Product product = proxy.checkProductAvailability(order.getItem().getProductId());
		if (product != null) {
			template.send(topic, order);
			repository.save(order);
		} else
			throw new OrderNotPlacedException("Product is out of stock");

	}
}
