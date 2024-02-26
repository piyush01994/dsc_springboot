package com.dsc.dsclife.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dsc.dsclife.entity.LoginEntity;

public class UserInfoDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	private List<GrantedAuthority> authorties;

	public UserInfoDetails(LoginEntity userEntity) {
		username = userEntity.getUserId();
		password = userEntity.getPassword();
		authorties = Arrays.stream(userEntity.getRole().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	

	}

	@Override public Collection<? extends GrantedAuthority> getAuthorities() { //
		return authorties;
	}

	@Override
	public String getPassword() { // TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() { // TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() { // TODO Auto-generated
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { // TODO Auto-generated method
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { // TODO Auto-generated
		return true;
	}

	@Override
	public boolean isEnabled() { // TODO Auto-generated method stub
		return true;
	}

}
