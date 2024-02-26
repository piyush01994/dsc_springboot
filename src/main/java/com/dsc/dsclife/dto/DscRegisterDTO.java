package com.dsc.dsclife.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class DscRegisterDTO {
	private long dscId;
	private String userId;
	private MultipartFile authoriationLetter;
	private MultipartFile firewallEntryform;
	private MultipartFile idCard;
	private MultipartFile logoForDsc;
	private MultipartFile logoForStamp;
	private MultipartFile stagingOrliveDceCertificate;
	private MultipartFile dscJar;
	private String password;
	private String serialNumber;
	private MultipartFile privateKey;
	private MultipartFile publicKey;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean status;
	private String fileName;
	private String validity;
	private String rejectReason;
	private String deptname;
	private String emailid;
	private String phonenumber;
	private String userName;
	private String deptType;
}
