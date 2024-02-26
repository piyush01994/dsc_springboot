package com.dsc.dsclife.entity;

import org.hibernate.annotations.ManyToAny;

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
@Table(name = "user_registration")
@Data
public class UserReigstrationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private  long id;
	@Column(length = 50)
	private String userName;
	@Column(length = 50, unique = true)
	private String userId;
	@Column(length = 50)
	private String designation;
	private Integer gender;
	@Column(length = 50)
	private String emailid;
	@Column(length = 10)
	private String mobile;
	@Column(length = 255)
	private String password;	 
	private Integer stateCode;
	private Integer districtCode;
	@Column(length = 50)
	private String dateOfBirth;
	@Column(length = 50)
	private String employeeId;
	private byte[]  photoId;
	private long organizationId;
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
	private String addressss;
	
	
	@OneToOne
	@JoinColumn(name = "organizationId", referencedColumnName = "organizationId",insertable = false,   updatable = false)
	private DepartmentRegistrationEntity departmentRegistrationEntity;
	
	
	
	

}
