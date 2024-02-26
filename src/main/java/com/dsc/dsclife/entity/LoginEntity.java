package com.dsc.dsclife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_logindetails")
@Data
public class LoginEntity {
	
	@Id
	private String userId;
	private String userName;
	private String role;
	private Boolean status;
	private Long stateCode;
	private String designation;
	private String password;
	private Long orgnizationid;
	private byte[]  photoId;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private String emailid;
	private String mobile;
	private String addressss;
	
	@OneToOne
	@JoinColumn(name = "orgnizationid", referencedColumnName = "organizationId",insertable = false,   updatable = false)
	private DepartmentRegistrationEntity departmentRegistrationEntity;
	
	
	
}
