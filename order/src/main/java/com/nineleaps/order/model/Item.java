package com.nineleaps.order.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UserDefinedType(value = "item")
public class Item {
	
	@Column(value ="product_id")
	private Integer productId;
	private Integer quantity;
	private Double price;

}
