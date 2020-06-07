package com.nineleaps.order.model;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType(value = "address")
public class Address {
	
	private String houseNo;
	private String street;
	private String city;
	private String state;
	private String country;
	private int pinCode;

}
