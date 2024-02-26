package com.dsc.dsclife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_dept_type")
@Data
public class DepartmentTypeEntity {
	
	@Id	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String departmentType;
	private boolean status;
	

}
