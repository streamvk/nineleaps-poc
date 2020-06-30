package com.nineleaps.supplier.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nineleaps.supplier.model.Order;
import com.nineleaps.supplier.model.Product;
import com.nineleaps.supplier.model.Supplier;
import com.nineleaps.supplier.service.SupplierService;
import com.nineleaps.supplier.service.talk.to.ProductProxy;

@RestController
public class SupplierController {
	
	Logger logger = LogManager.getLogger(SupplierController.class);

	@Autowired
	private SupplierService supplierService;
	//@Autowired
	//private SupplierMailService mailService;
	@Autowired
	private ProductProxy proxy;
	
	  @Autowired KafkaTemplate<String, Object> template;
	  private String topic = "nineleaps";
	 
	
	
	@PostMapping(path = "/create",consumes = { "application/json", "application/xml" })
	public ResponseEntity<?> createSupplier(@Valid @RequestBody Supplier supplier) {
		supplierService.saveSupplier(supplier);
		return new ResponseEntity<Supplier>(supplier,HttpStatus.CREATED);
	}

	//Only use when produce message through Kafka.
	@GetMapping(path ="/get/v1/{id}")
	public ResponseEntity<Supplier> getByIdV1(@PathVariable int id) {
		Supplier supplier= supplierService.findSupplierById(id);
		if (supplier !=null) {
			template.send(topic,supplier);
			return new ResponseEntity<Supplier>(supplier,HttpStatus.FOUND);
		}
		else
			return null;
	}
	
	@GetMapping(path ="/get/v2/{id}",produces = { "application/json", "application/xml" })
	public ResponseEntity<Supplier> getByIdV2(@PathVariable int id) {
		Supplier supplier= supplierService.findSupplierById(id);
		if (supplier !=null) {
			return new ResponseEntity<Supplier>(supplier,HttpStatus.FOUND);
		}
		else
			return null;
	}

	@PutMapping(path = "/update/{id}")
	public void updateById(@PathVariable int id,@RequestBody Supplier supplier) throws Exception {

		Supplier sup= supplierService.findSupplierById(id);
		if (sup != null)
			supplierService.updateSupplierDetails(supplier);
		else
			throw new Exception("Supplier not found by id.");
	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteById(@PathVariable int id) throws Exception {
		supplierService.deleteSupplierById(id);
	}

	@GetMapping(path = "/all",produces = { "application/json", "application/xml" })
	public ResponseEntity<List<Supplier>> getAll() {
		List<Supplier> list= supplierService.getAllSupplier();
		if(list != null & !list.isEmpty())
			return new ResponseEntity<List<Supplier>>(list,HttpStatus.FOUND);
		else
			return new ResponseEntity<List<Supplier>>(list,HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value="/isExists/{id}")
	public boolean isSupplierExists(@PathVariable int id) {
		Supplier supplier= supplierService.findSupplierById(id);
		if(supplier !=null)
			return true;
		else
			return false;
	}
	
	@GetMapping(path ="/get/v3/{id}",produces = { "application/json", "application/xml" })
	public Supplier getByIdV3(@PathVariable int id) {
		Supplier supplier= supplierService.findSupplierById(id);
		if (supplier !=null) {
			return supplier;
		}
		else
			return null;
	}
	
	@KafkaListener(topics = "nineleaps", groupId = "hilly", containerFactory = "supplierKafkaListenerFactory")
	public void getuser(Order order) throws Exception {
		
		Product product =proxy.checkSupplierAgainstProduct(order.getItem().getProductId());
		if(product == null | product.getPk().getId() <= 0 )
			throw new Exception("Supplier is not found againts  product");
		else
        		logger.error("Send  mail to supplier.");
		
		//mailService.sendMail("vkstream@hotmail.com", "Nineleaps Order", "Hello Vivek.");
	}
	
	@GetMapping(value="/loggerTest")
	public void loggerTest() {
		logger.info("......................Centeralized Logging Testing..........................");
	}
	
}
