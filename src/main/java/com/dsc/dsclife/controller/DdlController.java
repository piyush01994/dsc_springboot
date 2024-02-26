package com.dsc.dsclife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.DepartmentTypeDto;
import com.dsc.dsclife.dto.DepartmentddlDTO;
import com.dsc.dsclife.dto.OrganizationDto;
import com.dsc.dsclife.dto.Statedto;
import com.dsc.dsclife.service.DepartmentRegistrationService;
import com.dsc.dsclife.service.DepartmentTypeService;
import com.dsc.dsclife.service.OrganizationService;
import com.dsc.dsclife.service.StateService;

@RestController
@RequestMapping("/alluser/getdata")
public class DdlController {

	@Autowired
	private StateService service;
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private DepartmentTypeService departmentTypeService;
	
	@Autowired
	private DepartmentRegistrationService departmentRegistrationService;
	

	@GetMapping("/statelist")
	public ResponseEntity<List<Statedto>> getallstatedatabystatus() {
		List<Statedto> dto = this.service.getallstatedatabystatus();
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

	@GetMapping("/getorglist")
	public ResponseEntity<List<OrganizationDto>> getallorgnatizationbyscodeanddepttype(@RequestParam("statecode")long stateCode ,@RequestParam("orgtype")long deptType ) {
		List<OrganizationDto> dto = this.organizationService.getallorgnatizationbyscodeanddepttype(stateCode,deptType);
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}
	
	@GetMapping("/getalldata")
	public ResponseEntity<List<DepartmentTypeDto>> getalldepttype(){
		List<DepartmentTypeDto> dto = this.departmentTypeService.getalldepttype();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	// all  approved department 
			@GetMapping("/allapproveddata")
			public ResponseEntity<List<DepartmentddlDTO>> allapproveddata(){
				List<DepartmentddlDTO> dto = this.departmentRegistrationService.allapproveddatadepartment();
				return ResponseEntity.status(HttpStatus.OK).body(dto);
				
			}

			
			
}
