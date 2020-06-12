package com.nineleaps.supplier.exception;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.FeignException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(SupplierNotFoundException.class)
	public final ResponseEntity<Object> userNotFoundException(Exception ex,WebRequest request){
		ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false));
		
		return  new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(FeignException.BadRequest.class)  
    public Map<String, Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return new JSONObject(e.contentUTF8()).toMap(); 
    }

}
