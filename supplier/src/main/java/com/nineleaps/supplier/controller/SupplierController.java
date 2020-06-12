package com.nineleaps.supplier.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.supplier.exception.SupplierNotFoundException;
import com.nineleaps.supplier.model.Order;
import com.nineleaps.supplier.model.Product;
import com.nineleaps.supplier.model.Supplier;
import com.nineleaps.supplier.repository.SupplierRepository;
import com.nineleaps.supplier.service.talk.to.ProductProxy;

@RestController
public class SupplierController {

	@Autowired
	private SupplierRepository repository;
	//@Autowired
	//private SupplierMailService mailService;
	@Autowired
	private ProductProxy proxy;
	
	  @Autowired KafkaTemplate<String, Object> template;
	  private String topic = "nineleaps";
	 
	
	
	@PostMapping(path = "/create")
	public void createSupplier(@Valid @RequestBody Supplier supplier) {
		repository.save(supplier);
	}

	//Only use when produce message through Kafka.
	@GetMapping(path ="/get/v1/{id}")
	public Supplier getByIdV1(@PathVariable int id) {
		Optional<Supplier> op = repository.findById(id);
		if (op.isPresent()) {
			template.send(topic,op.get());
			return op.get();
		}
		else
			throw new SupplierNotFoundException("Supplier not found by id.");
	}
	
	@GetMapping(path ="/get/v2/{id}")
	public Supplier getByIdV2(@PathVariable int id) {
		Optional<Supplier> op = repository.findById(id);
		if (op.isPresent()) {
			return op.get();
		}
		else
			throw new SupplierNotFoundException("Supplier not found by id.");
	}

	@PutMapping(path = "/update/{id}")
	public void updateById(@PathVariable int id,@RequestBody Supplier supplier) throws Exception {

		Optional<Supplier> op = repository.findById(id);
		if (op.isPresent())
			repository.save(supplier);
		else
			throw new Exception("Supplier not found by id.");
	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteById(@PathVariable int id) throws Exception {
		repository.deleteById(id);
	}

	@GetMapping(path = "/all")
	public List<Supplier> getAll() {
		return repository.findAll();
	}
	
	
	@KafkaListener(topics = "nineleaps", groupId = "hilly", containerFactory = "supplierKafkaListenerFactory")
	public void getuser(Order order) throws Exception {
		
		Product product =proxy.checkSupplierAgainstProduct(order.getItem().getProductId());
		if(product == null | product.getPk().getId() <= 0 )
			throw new Exception("Supplier is not found againts  product");
		else
			System.out.println("Send  mail to supplier.");
		//mailService.sendMail("vkstream@hotmail.com", "Nineleaps Order", "Hello Vivek.");
	}
}
