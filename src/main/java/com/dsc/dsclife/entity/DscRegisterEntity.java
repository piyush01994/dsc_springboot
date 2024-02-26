package com.dsc.dsclife.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="tbl_dsc_register")
@Data
public class DscRegisterEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long dscId;
	private String userId;
	private byte[] authoriationLetter;
	private byte[] firewallEntryform;
	private byte[] idCard;
	private byte[] applicationForm;
	private byte[] logoForDsc;
	private byte[] logoForStamp;
	private byte[] stagingOrliveDceCertificate;
	private byte[] dscJar;
	private String password;
	private String serialNumber;
	private byte[] privateKey;
	private byte[] publicKey;
	private String createdBy;
	private String createdDate;
	private String updatedBy;
	private String updatedDate;
	private Boolean status;
	private String fileName;
	private String validity;
	private String rejectReason;
	
	@OneToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId",insertable = false,   updatable = false)
	private LoginEntity loginEntity;
	
	
	
	

}
