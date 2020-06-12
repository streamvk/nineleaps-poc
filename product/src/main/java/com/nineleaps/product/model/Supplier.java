package com.nineleaps.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
	private int id;
	private String name;
	private String email;
}
