package com.dsc.dsclife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.UserRegistrationDTO;
import com.dsc.dsclife.service.UserReigstrationService;

@RestController
@RequestMapping("/alluser/userRegistration")
public class UserRegistrationController {

	@Autowired
	private UserReigstrationService reigstrationService;

	// save user registration--
	@PostMapping("/savedata")
	public ResponseEntity<String> savedeptdata(@RequestBody UserRegistrationDTO dtodata) {
		String dto = this.reigstrationService.savedata(dtodata);
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}
	
	
	

}
