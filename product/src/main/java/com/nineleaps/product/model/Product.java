package com.nineleaps.product.model;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;
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
	@NotNull(message="Product id can not be null")
	private int id;
	@Size(min = 3, max = 50)
	private String name;
	@NegativeOrZero
	private Double price ;
	@Size(min = 10, max = 200)
	private String description;
	@NotNull(message="Supplier id can not be null")
	private int supplierId;
	

}
