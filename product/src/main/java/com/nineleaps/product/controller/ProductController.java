package com.nineleaps.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.nineleaps.product.service.ProductService;
import com.nineleaps.product.service.talk.to.SupplierProxy;

@RestController
public class ProductController {
	
	Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierProxy proxy;

	Supplier supplierTopic = null;

	@PostMapping(path = "/create",consumes = { "application/json", "application/xml" })
	public ResponseEntity<?> createProduct(@Valid @RequestBody Product product){
		boolean isCreated =productService.saveProduct(product);
		if(isCreated)
		return new ResponseEntity<String>("Product Is Created.",HttpStatus.CREATED);
		else
			return new ResponseEntity<String>("Some Problem In Product.",HttpStatus.NOT_ACCEPTABLE);
	}
	
	@GetMapping(path = "/get/{id}",produces = { "application/json", "application/xml" })
	public ResponseEntity<Product> getById(@PathVariable int id) throws Exception{
		Product product = productService.findProductByPkId(id);
		if (product != null) {
			int supplierId = proxy.checkSupplierIsAlive(product.getPk().getSupplierId()).getId();
			if (supplierId >0)
				return new ResponseEntity<Product>(product,HttpStatus.FOUND);
			else
				throw new Exception("Supplier is not available.");

		} else
			return new ResponseEntity<Product>(product,HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/get/v2/{id}",produces = { "application/json", "application/xml" })
	public Product getProductById(@PathVariable int id) throws Exception{
		Product product = productService.findProductByPkId(id);
		if (product != null) {
			int supplierId = proxy.checkSupplierIsAlive(product.getPk().getSupplierId()).getId();
			if (supplierId >0)
				return product;
			else
				throw new Exception("Supplier is not available.");

		} else
			return null;
	}

	@GetMapping(path = "/all",produces = { "application/json", "application/xml" })
	public ResponseEntity<List<Product>> getAll() throws Exception {
		List<Product> list=productService.findAllProduct();
		 return new ResponseEntity<List<Product>>(list,HttpStatus.FOUND);
	}

	@PutMapping(path = "/update")
	public void updtaeById(@RequestBody Product product) throws Exception {
		productService.updtaeProductById(product);
	}

	@DeleteMapping(path = "/delete/{id}")
	public void deleteById(@PathVariable int id) {
	Product product = productService.findProductByPkId(id);
	productService.deleteProduct(product);
	}
	
	@GetMapping(value="/isAvailable/{id}")
	public boolean isProductAvailable(@PathVariable int id) {
		boolean isAvailable = productService.isProductAvailable(id);
		if(isAvailable)
			return true;
		else
			return false;
	}
	
	// Used For Kafka Testing.  
	@KafkaListener(topics = "nineleaps", groupId = "vivek", containerFactory = "userKafkaListenerFactory")
	public Supplier getuser(Supplier supplier) {
		supplierTopic = supplier;
		return supplierTopic;
	}

}
