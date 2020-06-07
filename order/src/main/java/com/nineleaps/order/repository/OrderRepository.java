package com.nineleaps.order.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.nineleaps.order.model.Order;

public interface OrderRepository extends CassandraRepository<Order, Integer>{

}
