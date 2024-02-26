package com.dsc.dsclife.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.DscRegisterDTO;
import com.dsc.dsclife.dto.UserRegistrationDTO;
import com.dsc.dsclife.service.DscRegisterService;
import com.dsc.dsclife.service.UserReigstrationService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/user/dscregister")
public class DscRegistrationController {
	
	@Autowired
	private UserReigstrationService userserivce;

	@Autowired
	private DscRegisterService reigstrationService;
	
	
	@PostMapping("/savedata")
	public ResponseEntity<DscRegisterDTO> savedeptdata(@ModelAttribute DscRegisterDTO dtodata,@RequestHeader("Authorization") String tokenHeader) throws IOException{
		DscRegisterDTO dto = this.reigstrationService.savedata(dtodata,tokenHeader);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	
	// get all detail of user dsc information 
	
	@GetMapping("/getdscdata")
	public ResponseEntity<List<DscRegisterDTO>> getdscdata(@RequestHeader("Authorization") String tokenHeader) {
		List<DscRegisterDTO> dto = this.reigstrationService.getdscdata(tokenHeader);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	

	
	// get dsc file data in single pdf---
	
	@GetMapping("/getdsczip/{id}")
	public ResponseEntity<ByteArrayResource> getfiles(@PathVariable("id") Long id)throws IOException{
		byte filedata[]  = this.reigstrationService.getdsczip(id);
		String filename ="firewall_entry_form.pdf";
		 HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentLength(filedata.length)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new ByteArrayResource(filedata));
		
		
	}
	
	@GetMapping("/getuserdata")
	public ResponseEntity<UserRegistrationDTO> getuserdata(@RequestHeader("Authorization") String tokenHeader) {
		UserRegistrationDTO dto = this.userserivce.getdscdata(tokenHeader);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
}
