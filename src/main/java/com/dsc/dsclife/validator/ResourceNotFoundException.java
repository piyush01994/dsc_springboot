package com.dsc.dsclife.validator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResourceNotFoundException extends RuntimeException {
	String resource;
	String fieldname;
	long fieldvalue;
	public ResourceNotFoundException(String resource,String fieldname,long fieldvalue) {
		super(String.format("%s not found with %s:%s", resource,fieldname,fieldvalue));
		this.resource=resource;
		this.fieldname=fieldname;
		this.fieldvalue=fieldvalue;
		
	}

}
