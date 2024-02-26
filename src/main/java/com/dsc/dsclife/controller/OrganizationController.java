package com.dsc.dsclife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.OrganizationDto;
import com.dsc.dsclife.service.OrganizationService;

@RestController
@RequestMapping("/admin/organization")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	
	@GetMapping("/getdata")
	public ResponseEntity<List<OrganizationDto>> getallorgnatization(){
		List<OrganizationDto> dto = this.organizationService.getallorgnatization();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	@PostMapping("/savedata")
	public ResponseEntity<String> saveorgnizationdata(@RequestBody OrganizationDto dtodata){
		String msg = this.organizationService.saveorgnizationdata(dtodata);
		return ResponseEntity.status(HttpStatus.OK).body(msg);
		
	}
		
	@GetMapping("/getdata/{code}")
	public ResponseEntity<OrganizationDto> getorgnizationdatabycode(@PathVariable("code") Long orgcode){
		OrganizationDto dto = this.organizationService.getorgnizationdatabycode(orgcode);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}

}
