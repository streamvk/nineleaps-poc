package com.nineleaps.product.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.nineleaps.product.model.Product;

public interface ProductRepository extends CassandraRepository<Product, Integer>{

}
