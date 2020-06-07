package com.nineleaps.supplier.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
	
	@PrimaryKey
	private int id;
	private String name;
	private String email;

}
