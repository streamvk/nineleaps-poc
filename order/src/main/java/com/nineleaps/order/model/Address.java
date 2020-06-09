package com.nineleaps.order.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType(value = "address")
public class Address {
	
	@NotBlank
	private String houseNo;
	@NotBlank
	private String street;
	@NotBlank
	private String city;
	@NotBlank
	private String state;
	@NotBlank
	private String country;
	@NotBlank
	private int pinCode;

}
