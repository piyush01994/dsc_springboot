package com.dsc.dsclife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_dept_registration")
@Data
public class DepartmentRegistrationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private  long  organizationId;
	@Column(length = 100)
	private  String organizationName;
	private Long departmentid;
	@Column(length = 50)
	private String hodName;
	@Column(length = 50)
	private String emailId;
	@Column(length = 10)
	private String mobile;
	@Column(length = 250)
	private String address;
	@Column(length = 50)
	private String designation;
	@Column(name = "userId", unique = true)
	private String userId;
	@Column(length = 250)
	private String password;	
	private boolean status;
	@Column(length = 50)
	private String createdBy;
	@Column(length = 50)
	private String createdDate;
	@Column(length = 50)
	private String updatedBy;
	@Column(length = 50)
	private String updatedDate;
	private String role;
	private String rejectedReason;

	
	@OneToOne
	@JoinColumn(name = "departmentid", referencedColumnName = "departmentid",insertable = false,   updatable = false)
	private OrganizationEntity organizationEntity;
	
	
	
	
	
	
	

}
