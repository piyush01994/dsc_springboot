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

import com.dsc.dsclife.dto.DepartmentRegistrationDTO;
import com.dsc.dsclife.dto.Statedto;
import com.dsc.dsclife.service.StateService;

@RestController
@RequestMapping("/admin/state")
public class StateController {
	
	@Autowired
	private StateService service;
	
	@GetMapping("/getdata")
	public ResponseEntity<List<Statedto>> getallstatedata(){
		List<Statedto> dto = this.service.getallstatedata();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	@PostMapping("/savedata")
	public ResponseEntity<Statedto> savestatedata(@RequestBody Statedto dtodata){
		Statedto dto = this.service.savestatedata(dtodata);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	@GetMapping("/getdata/{statecode}")
	public ResponseEntity<Statedto> getstatedata(@PathVariable("statecode") Long stateCode){
		Statedto dto = this.service.getstatedata(stateCode);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}

}
