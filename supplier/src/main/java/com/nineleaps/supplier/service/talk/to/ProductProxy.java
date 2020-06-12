package com.nineleaps.supplier.service.talk.to;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nineleaps.supplier.model.Product;



@FeignClient(name="nineleaps-gateway") // it'll worke with zuul gateway
//@FeignClient(name="nineleaps-registry", url="localhost:1020")
@RibbonClient(name="product")
public interface ProductProxy {
	
// complete URL is "/product/product/get/{id}" product is coming 2 times because 1st is the application name and 2nd is context.
	@GetMapping("/product/product/get/{id}")
	public Product checkSupplierAgainstProduct( @PathVariable int id);

}

