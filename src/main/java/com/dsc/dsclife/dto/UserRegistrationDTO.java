package com.dsc.dsclife.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
	
	private  long id;
	private String userName;
	private String userId;
	private String designation;
	private Integer gender;
	private String emailid;
	private String mobile;
	private String password;
	private String stateName;
	private String dateOfBirth;
	private byte[]  photoId;
	private long organizationId;
	private Integer stateCode;
	private boolean status;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private String role;
	private String addressss;
	
	private String orgnizationName;
	private String depttype;

	

}
