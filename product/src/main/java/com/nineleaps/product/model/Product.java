package com.nineleaps.product.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Product {
	
	@PrimaryKey
	private int id;
	private String name;
	private Double price ;
	private String description;
	private int supplierId;
	

}
