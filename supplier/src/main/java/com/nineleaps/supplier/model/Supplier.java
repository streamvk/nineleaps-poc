package com.nineleaps.supplier.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull(message="Supplier id can not be null")
	private int id;
	@Size(min=3, max=50)
	private String name;
	@Email
	private String email;

}
