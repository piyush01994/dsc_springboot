package com.dsc.dsclife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.DepartmentRegistrationDTO;
import com.dsc.dsclife.service.DepartmentRegistrationService;

@RestController
@RequestMapping("/admin/approvedOrgnization")
public class ApprovedOrganizationController {
	
	@Autowired
	private DepartmentRegistrationService departmentRegistrationService;
	
	@GetMapping("/approveddata")
	public ResponseEntity<DepartmentRegistrationDTO> savedeptdata(@RequestParam("id") long id ,@RequestParam("status") Boolean status,@RequestParam("reason") String reason){
		DepartmentRegistrationDTO dto = this.departmentRegistrationService.approveddata(id,status,reason);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	// all non approved department 
	@GetMapping("/allnonapproveddata")
	public ResponseEntity<List<DepartmentRegistrationDTO>> allnonapproveddata(){
		List<DepartmentRegistrationDTO> dto = this.departmentRegistrationService.allnonapproveddata();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	
	// all  approved department 
		@GetMapping("/allapproveddata")
		public ResponseEntity<List<DepartmentRegistrationDTO>> allapproveddata(){
			List<DepartmentRegistrationDTO> dto = this.departmentRegistrationService.allapproveddata();
			return ResponseEntity.status(HttpStatus.OK).body(dto);
			
		}
		
		
		@GetMapping("/getdatabyid/{id}")
		public ResponseEntity<DepartmentRegistrationDTO> getdatabyid(@PathVariable("id") long id ){
			DepartmentRegistrationDTO dto = this.departmentRegistrationService.getdatabyid(id);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
			
		}

}
