package com.nineleaps.product.model;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@PrimaryKeyClass
public class ProductPrimaryKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@PrimaryKeyColumn(name = "ID", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	  private int id;
	 
	 @PrimaryKeyColumn(name = "SUPPLIER_ID", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	 private int supplierId; 
}
