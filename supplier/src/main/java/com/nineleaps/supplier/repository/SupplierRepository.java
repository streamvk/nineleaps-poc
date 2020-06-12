package com.nineleaps.supplier.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.nineleaps.supplier.model.Supplier;

public interface SupplierRepository extends CassandraRepository<Supplier,Integer>{
	

}
