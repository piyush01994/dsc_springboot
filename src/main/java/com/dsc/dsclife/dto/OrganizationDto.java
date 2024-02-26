package com.dsc.dsclife.dto;

import lombok.Data;

@Data
public class OrganizationDto {
	
	private long id;
	private long departmentid;
	private String departmentName;
	private long deptType;
	private long stateCode;
	private String deptypename;
	private String stateName;

}
