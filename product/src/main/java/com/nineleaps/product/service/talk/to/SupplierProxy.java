package com.nineleaps.product.service.talk.to;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nineleaps.product.model.Supplier;


//@FeignClient(name="nineleaps-registry") // it'll worke with zuul gateway
@FeignClient(name="nineleaps-registry", url="localhost:1010")
@RibbonClient(name="supplier")
public interface SupplierProxy {

	@GetMapping("/supplier/get/{id}")
	public Supplier checkSupplierIsAlive( @PathVariable int id);
}
