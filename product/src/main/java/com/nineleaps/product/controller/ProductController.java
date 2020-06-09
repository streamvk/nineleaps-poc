package com.nineleaps.product.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.product.model.Product;
import com.nineleaps.product.model.Supplier;
import com.nineleaps.product.repository.ProductRepository;
import com.nineleaps.product.service.talk.to.SupplierProxy;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private SupplierProxy proxy;

	Supplier supplierTopic = null;

	@PostMapping(path = "/create")
	public void createProduct(@Valid @RequestBody Product product) {
		product.setSupplierId(supplierTopic.getId());
		repository.save(product);
	}

	@GetMapping(path = "/get/{id}")
	public Product getById(@PathVariable int id) throws Exception {

		Optional<Product> op = repository.findById(id);
		if (op.isPresent()) {
			Product product=op.get();
			Supplier supplier = proxy.checkSupplierIsAlive(product.getSupplierId());
			if (supplier != null)
				return product;
			else
				throw new Exception("Supplier is not available.");
				
		} else
			throw new Exception("Product not found by id.");
	}

	@GetMapping(path = "/all")
	public List<Product> getAll() throws Exception {
		return repository.findAll();
	}

	@PutMapping(path = "/update")
	public void updtaeById(@RequestBody Product product) throws Exception {
		repository.save(product);
	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteById(@PathVariable int id) {
		repository.deleteById(id);
	}

	@KafkaListener(topics = "nineleaps", groupId = "vivek", containerFactory = "userKafkaListenerFactory")
	public Supplier getuser(Supplier supplier) {
		supplierTopic = supplier;
		return supplierTopic;
	}

	@GetMapping("/consumeJsonMessage")
	public Supplier consumeJsonMessage() {
		return supplierTopic;
	}
}
