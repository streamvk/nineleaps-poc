package com.nineleaps.product.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8734617896676776255L;
	
	public ProductNotFoundException(String message){
		super(message);
	}

}
