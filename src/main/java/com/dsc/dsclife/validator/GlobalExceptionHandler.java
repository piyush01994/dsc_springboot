package com.dsc.dsclife.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dsc.dsclife.dto.ApiPayload;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<com.dsc.dsclife.dto.ApiPayload>resourceNotFoundException(ResourceNotFoundException rs ){
		String msg =rs.getMessage();
		ApiPayload apidata = new ApiPayload(msg, false);
		return new ResponseEntity(apidata, HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>methodArgsNotValidException(MethodArgumentNotValidException rs )
	{
		Map<String, String> resp =new HashMap<>();
		rs.getBindingResult().getAllErrors().forEach((error)->{
		String fieldname =	((FieldError)error).getField();
		String messsage = error.getDefaultMessage();
		resp.put(fieldname, messsage);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		
	}

	@ExceptionHandler(DataAlreadyExistException.class)
	public ResponseEntity<String>resourcealreadyExistException(DataAlreadyExistException rs ){
		String msg =rs.getMessage();
		return new ResponseEntity(msg, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String>BadCredentialsException(BadCredentialsException rs ){
		String msg =rs.getMessage();
		ApiPayload apidata = new ApiPayload(msg, false);
		return new ResponseEntity(apidata, HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<String>UsernameNotFoundException(UsernameNotFoundException rs ){
		String msg =rs.getMessage();
		ApiPayload apidata = new ApiPayload(msg, false);
		return new ResponseEntity(apidata, HttpStatus.NOT_FOUND);
		
	}
	
	

}
