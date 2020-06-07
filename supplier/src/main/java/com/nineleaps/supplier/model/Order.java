package com.nineleaps.supplier.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	private Integer id;
	private Date date;
	private String customerName;
	private String customerEmail;
	private Address customerAddress;
	private Item item;
	private Integer total;

}
