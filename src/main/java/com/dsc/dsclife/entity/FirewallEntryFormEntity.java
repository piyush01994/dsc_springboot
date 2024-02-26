package com.dsc.dsclife.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tbl_firewall")
@Data
public class FirewallEntryFormEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String serverName;
	private String privateIp;	
	private String purpose;
	private String publicIp;
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
