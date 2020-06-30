package com.nineleaps.product.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.nineleaps.product.model.Product;
import com.nineleaps.product.model.ProductPrimaryKey;

@Repository
public interface ProductRepository extends CassandraRepository<Product, ProductPrimaryKey>{
	
	Product findByPkId(int id);
	void deleteProductByPkId(int id);

}
