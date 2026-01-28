package com.employeespringbootglobalexceptionhandling.demo.advice;

import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.employeespringbootglobalexceptionhandling.demo.custom.exception.BusinessException;
import com.employeespringbootglobalexceptionhandling.demo.custom.exception.EmptyInputException;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException) {
		return new ResponseEntity<String>("Input Field is Empty, please look into it ", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleBusinessException(BusinessException businessException) {
		return new ResponseEntity<String>(businessException.getErrorMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noElementExits(NoSuchElementException noSuchElementException) {
		return new ResponseEntity<String>("No value is present in DB, please change your request",
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> handleEmptyResult(EmptyResultDataAccessException emptyResultDataAccessException) {
		return new ResponseEntity<String>("No Employee is present with this Id in DB, please change the EmpId",
				HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
	        HttpRequestMethodNotSupportedException ex,
	        HttpHeaders headers,
	        HttpStatusCode status,    // ✅ Use this instead of HttpStatus
	        WebRequest request) {

	    return new ResponseEntity<>("Please change your HTTP method Type", HttpStatus.METHOD_NOT_ALLOWED);
	}

}
