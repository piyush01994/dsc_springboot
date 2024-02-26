package com.dsc.dsclife.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.dto.FirewallEntryFormDto;
import com.dsc.dsclife.service.FirewallService;

@RestController
@RequestMapping("/user/firewall")
public class FirewallEntryFormController {
	
	@Autowired
	private FirewallService firewalservice;
	
	@PostMapping("/savedata")
	public ResponseEntity<ByteArrayResource> savedeptdata(@RequestBody FirewallEntryFormDto dtodata) throws IOException{
		byte filedata[] = this.firewalservice.savedata(dtodata);
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
	
	@GetMapping("/getnodaloffcerpdf")
	public ResponseEntity<ByteArrayResource> getpdf(@RequestHeader("Authorization") String tokenHeader) throws IOException{
		byte filedata[]  = this.firewalservice.getpdf(tokenHeader);
		String filename ="pdffile.pdf";
		 HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentLength(filedata.length)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new ByteArrayResource(filedata));
		
	}
	
	
	@GetMapping("/getfirewallpdfform/{id}")
	public ResponseEntity<ByteArrayResource> getpdf(@PathVariable("id") long id) throws IOException{
		byte filedata[]  = this.firewalservice.getfirewallpdf(id);
		String filename ="firewall.pdf";
		 HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+filename);

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentLength(filedata.length)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(new ByteArrayResource(filedata));
		
	}
	
	
	@GetMapping("/getapplicationform")
	public ResponseEntity<ByteArrayResource> applicationform(@RequestHeader("Authorization") String tokenHeader) throws IOException{
		byte filedata[]  = this.firewalservice.applicationform(tokenHeader);
		String filename ="applicationform.pdf";
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
