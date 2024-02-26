 package com.dsc.dsclife.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsc.dsclife.entity.LoginEntity;
import com.dsc.dsclife.repository.UserLoginRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth")
@CrossOrigin("http://164.100.137.235:8081")
public class AuthController {
	
	@Autowired
	private  CustomUserDetailService userDetailsService;
	
	@Autowired	
	private AuthenticationManager manager;
	
	
	
	
	@Autowired
	private UserLoginRepository userLoginDetailRepository;
	
	@Autowired
	private JwtHelper jwthelper;
	
	
	  private Logger logger = LoggerFactory.getLogger(AuthController.class);
	  
	  
	  
	  @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request,HttpServletRequest servlet) {	
		  
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserid());
	        this.doAuthenticate(request.getUserid(), request.getPassword());	      
	        LoginEntity logindata = this.userLoginDetailRepository.findByUserId(request.getUserid());
	        String token = this.jwthelper.generateToken(userDetails,logindata.getRole());	       
	        JwtResponse response = JwtResponse
	        		.builder()
	        		.jwtToken(token)
	                .username(userDetails.getUsername())
	                .authorties((List<GrantedAuthority>) userDetails.getAuthorities()).build();
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	  
	
	  	
			private void doAuthenticate(String userid, String password) {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userid,
					password);
			try {
				
				manager.authenticate(authentication);				

			} catch (BadCredentialsException e) {
				
				throw new BadCredentialsException(" Invalid  Password  !!, total attempts:- ");
			}

		}
	    
	
	
	   
	  
	 
	    
	   

}
