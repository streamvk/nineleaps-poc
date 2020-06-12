package com.nineleaps.order.model;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(value ="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	@PrimaryKey
	@NotNull(message="Order id can not be null")
	private Integer id;
	//@FutureOrPresent
	private Date date;
	@Size(min=3, max=50)
	@Column(value ="customer_name")
	private String customerName;
	@Email
	@Column(value ="customer_email")
	private String customerEmail;
	@CassandraType(type = DataType.Name.UDT, userTypeName = "address")
	@Column(value ="customer_address")
	private Address customerAddress;
	@CassandraType(type = DataType.Name.UDT, userTypeName = "item")
	@Column(value ="items")
	private Item item;
	private Integer total;
	
	

}
