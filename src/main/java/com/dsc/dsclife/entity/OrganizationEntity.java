package com.dsc.dsclife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_orgnization")
@Data
public class OrganizationEntity {
	
	@Id
	private long departmentid;
	private String departmentName;
	private Long deptType;
	private long stateCode;
	
	
	@ManyToOne
	@JoinColumn(name = "stateCode", referencedColumnName = "stateCode",insertable = false,   updatable = false)
	private StateEntity StateEntity;
	
	@ManyToOne
	@JoinColumn(name = "deptType", referencedColumnName = "id",insertable = false,   updatable = false)
	private DepartmentTypeEntity departmentTypeEntity;

}
