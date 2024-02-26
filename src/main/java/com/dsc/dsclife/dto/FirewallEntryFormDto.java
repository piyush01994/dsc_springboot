package com.dsc.dsclife.dto;

import lombok.Data;

@Data
public class FirewallEntryFormDto {
	
	private Long id;
	private String serverName;
	private String privateIp;	
	private String publicIp;
	private String purpose;
	private String typeOfServer;
	private String accessToBeAllowedAnyspecificipRange;	
	private String portServiceToAllowed;
	private String nameOfApplicaint;
	private String phoneNumber;
	private String email;
	private String userId;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	


}
