package com.nineleaps.order.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
	
	@NotNull
	@Column(value ="productid")
	private Integer productId;
	@Positive
	private Integer quantity;
	@PositiveOrZero
	private BigDecimal price;

}
