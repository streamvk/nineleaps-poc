package com.nineleaps.product.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nineleaps.product.controller.ProductController;
import com.nineleaps.product.exception.FeignExceptionHandle;
import com.nineleaps.product.exception.ProductNotFoundException;
import com.nineleaps.product.model.Product;
import com.nineleaps.product.repository.ProductRepository;
import com.nineleaps.product.service.talk.to.SupplierProxy;

@Service
public class ProductService {

	Logger logger = LogManager.getLogger(ProductController.class);

	@Autowired
	private ProductRepository repository;
	@Autowired
	private SupplierProxy proxy;

	public boolean saveProduct(Product product) {
		boolean result=false;
		try {
			int supplierId = proxy.checkSupplierIsAlive(product.getPk().getSupplierId()).getId();
			if (supplierId >0) {
				repository.save(product);
				result= true;
			}
			else {
			result= false;
			}
		} catch (Exception ex) {
			logger.error("Supplier Services {}", ex.getMessage());
		}
		return result;
	}

	public Product findProductByPkId(int id) {

		Product product = repository.findByPkId(id);
		if (product != null)
			return product;
		else
			return null;
	}

	public List<Product> findAllProduct() throws Exception {

		List<Product> list = repository.findAll();
		if (list != null && !list.isEmpty())
			return list;
		else
			throw new ProductNotFoundException("No Product is available.");
	}

	public void updtaeProductById(Product product) {
		repository.save(product);
	}

	public void deleteProduct(Product product) {
		repository.delete(product);
	}
	
	public boolean isProductAvailable(int id) {
		Optional<Product> sup=Optional.ofNullable(repository.findByPkId(id));
		if(sup.isPresent())
			return true;
		else
			return false;
	}

}
