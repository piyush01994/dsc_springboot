package com.dsc.dsclife.dto;

import lombok.Data;

@Data
public class Statedto {
	
	private Long sno;	
	private Long stateCode;	
	private String stateName;
	private boolean status;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updateddate;

}
