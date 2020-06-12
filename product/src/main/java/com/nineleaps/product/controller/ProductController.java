package com.nineleaps.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.product.exception.FeignExceptionHandle;
import com.nineleaps.product.exception.ProductNotFoundException;
import com.nineleaps.product.model.Product;
import com.nineleaps.product.model.Supplier;
import com.nineleaps.product.repository.ProductRepository;
import com.nineleaps.product.service.talk.to.SupplierProxy;

@RestController
public class ProductController {
	
	Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private ProductRepository repository;
	@Autowired
	private SupplierProxy proxy;

	Supplier supplierTopic = null;

	@PostMapping(path = "/create")
	public void createProduct(@Valid @RequestBody Product product){
		try {
		int supplierId = proxy.checkSupplierIsAlive(product.getPk().getSupplierId()).getId();
		if(supplierId >0)
			repository.save(product);
		else
			throw new FeignExceptionHandle("Supplier Serivce Feign Exception");
		}catch(FeignExceptionHandle ex) {
			logger.error("Supplier Services {}",ex.getMessage());
		}
	}
	
	@GetMapping(path = "/get/{id}")
	public Product getById(@PathVariable int id) throws Exception{
		Product op = repository.findByPkId(id);
		if (op != null) {
			Supplier supplier = proxy.checkSupplierIsAlive(op.getPk().getSupplierId());
			if (supplier != null)
				return op;
			else
				throw new Exception("Supplier is not available.");

		} else
			throw new ProductNotFoundException("Product not found by id.");
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
	Product product = repository.findByPkId(id);
	  repository.delete(product);
	}
	
	
	// Used For Kafka Testing.  
	@KafkaListener(topics = "nineleaps", groupId = "vivek", containerFactory = "userKafkaListenerFactory")
	public Supplier getuser(Supplier supplier) {
		supplierTopic = supplier;
		return supplierTopic;
	}

}
