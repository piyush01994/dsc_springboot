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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.DscRegisterDTO;
import com.dsc.dsclife.service.DscRegisterService;

@RestController
@RequestMapping("/admin/approvedDsc")
public class ApproveDscController {
	
	
	@Autowired
	private DscRegisterService reigstrationService;
	
	
	// dsc details for approval--
	
	@GetMapping("/getdscdata")
	public ResponseEntity<List<DscRegisterDTO>> savedeptdata(){
		List<DscRegisterDTO> dto = this.reigstrationService.getdata();
		return ResponseEntity.status(HttpStatus.OK).body(dto);
		
	}
	
	
	// dsc details of approved dsc--
	
		@GetMapping("/getapproveddscdata")
		public ResponseEntity<List<DscRegisterDTO>> getapproveddscdata(){
			List<DscRegisterDTO> dto = this.reigstrationService.getapproveddscdata();
			return ResponseEntity.status(HttpStatus.OK).body(dto);
			
		}
		
		
		// upload dsc jar and password and approved data \
		@PostMapping("/approveddsc")
		public ResponseEntity<DscRegisterDTO> approveddsc(@ModelAttribute DscRegisterDTO dscRegisterDTO) throws IOException{
			DscRegisterDTO dto = this.reigstrationService.approveddsc(dscRegisterDTO);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
			
		}

		// get dsc file data in single pdf---
		
		@GetMapping("/getfiles/{id}")
		public ResponseEntity<ByteArrayResource> getfiles(@PathVariable("id") Long id) throws IOException{
			byte filedata[]  = this.reigstrationService.getfiles(id);
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
}
