package com.dsc.dsclife.config;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {
	
	
private String jwtToken;
private String username;
private List<GrantedAuthority> authorties;


}
