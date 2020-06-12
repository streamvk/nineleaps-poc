package com.nineleaps.product.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import feign.FeignException;

@ResponseStatus
public class FeignExceptionHandle extends FeignException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FeignExceptionHandle(String message) {
		super(400,message);
	}

}
