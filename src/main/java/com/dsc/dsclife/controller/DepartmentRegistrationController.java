package com.dsc.dsclife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.DepartmentRegistrationDTO;
import com.dsc.dsclife.service.DepartmentRegistrationService;

@RestController
@RequestMapping("/alluser/departmentRegistration")
public class DepartmentRegistrationController {
	@Autowired
	private DepartmentRegistrationService departmentRegistrationService;
	
	@PostMapping("/savedata")
	public ResponseEntity<String> savedeptdata(@RequestBody DepartmentRegistrationDTO dtodata){
		String dto = this.departmentRegistrationService.savedata(dtodata);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}

}
