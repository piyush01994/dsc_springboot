package com.dsc.dsclife.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dsc.dsclife.entity.LoginEntity;
import com.dsc.dsclife.repository.UserLoginRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserLoginRepository userloginrepo;


	
	
	@Override
	public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
		Optional<LoginEntity> userdetails = userloginrepo.findById(userid);
		return userdetails.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + userid));

	}
}
