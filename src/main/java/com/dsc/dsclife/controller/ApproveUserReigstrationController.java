package com.dsc.dsclife.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.UserRegistrationDTO;
import com.dsc.dsclife.service.UserReigstrationService;

@RestController
@RequestMapping("/dept/approveduser")
public class ApproveUserReigstrationController {

	@Autowired
	private UserReigstrationService reigstrationService;

	@PostMapping("/approveuser")
	public ResponseEntity<UserRegistrationDTO> savedeptdata(@RequestParam("id") long id,
			@RequestParam("status") Boolean status) {
		UserRegistrationDTO dto = this.reigstrationService.approveuser(id, status);
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

	// all non approved user
	@GetMapping("/allnonapproveddata")
	public ResponseEntity<List<UserRegistrationDTO>> allnonapproveddata(
			@RequestHeader("Authorization") String tokenHeader) {
		List<UserRegistrationDTO> dto = this.reigstrationService.allnonapproveddata(tokenHeader);
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

	// all approved user
	@GetMapping("/allapproveddata")
	public ResponseEntity<List<UserRegistrationDTO>> allapproveddata(
			@RequestHeader("Authorization") String tokenHeader) {
		List<UserRegistrationDTO> dto = this.reigstrationService.allapproveddata(tokenHeader);
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}
	
	@GetMapping("getdatabyid/{id}")
	public ResponseEntity<UserRegistrationDTO> getdatabyid(@PathVariable("id") long id) {
		UserRegistrationDTO dto = this.reigstrationService.getdatabyid(id);
		return ResponseEntity.status(HttpStatus.OK).body(dto);

	}

}
