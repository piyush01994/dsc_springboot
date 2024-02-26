package com.dsc.dsclife.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_state")
public class StateEntity {
	
	
	@Id
	private Long stateCode;
	@Column(length = 50)
	private String stateName;
	private boolean status;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updateddate;
	
	

}
