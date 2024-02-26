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

import com.dsc.dsclife.dto.DepartmentTypeDto;
import com.dsc.dsclife.service.DepartmentTypeService;

@RestController
@RequestMapping("/admin/depttype")
public class DepartmentTypeController {
	
	@Autowired
	private DepartmentTypeService departmentTypeService;
	
	
	@GetMapping("/getalldata")
	public ResponseEntity<List<DepartmentTypeDto>> getalldepttype(){
		List<DepartmentTypeDto> dto = this.departmentTypeService.getalldepttype();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	@PostMapping("/savedata")
	public ResponseEntity<DepartmentTypeDto> savedeptype(@RequestBody DepartmentTypeDto dtodata){
		DepartmentTypeDto dto = this.departmentTypeService.savedeptype(dtodata);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	@GetMapping("/getdata/{id}")
	public ResponseEntity<DepartmentTypeDto> getalldepttypebyid(@PathVariable("id") Long id){
		DepartmentTypeDto dto = this.departmentTypeService.getalldepttypebyid(id);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}


}
