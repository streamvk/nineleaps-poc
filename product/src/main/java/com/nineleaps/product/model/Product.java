package com.nineleaps.product.model;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

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
	private ProductPrimaryKey pk; 
	@Size(min = 3, max = 50)
	private String name;
	@PositiveOrZero
	private Double price ;
	@Size(min = 10, max = 200)
	private String description;
	
	

}
