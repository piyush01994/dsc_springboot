package com.dsc.dsclife.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Autowired
private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

@Autowired
private JwtAuthenticationFilter jwtfilter;

@Autowired
private UserDetailsService userdetailservice;
	
@Bean
public SecurityFilterChain filterchan(HttpSecurity httpsecurity) throws Exception {
	httpsecurity.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> 	auth
			.requestMatchers("/alluser/**").permitAll()
			.requestMatchers("/auth/**").permitAll()
			.requestMatchers("/user/**").hasRole("USER")
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.requestMatchers("/dept/**").hasRole("DEPT")
			.anyRequest().authenticated())
			.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
			httpsecurity.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

	return httpsecurity.build();

}
	
	@Bean
	public PasswordEncoder passwordencoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	        return builder.getAuthenticationManager();
	    }
	 
	 @Bean
		public AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(userdetailservice);
			daoAuthenticationProvider.setPasswordEncoder(passwordencoder());
			return daoAuthenticationProvider; 
		}
		
 
	 
	 @Bean
	    public CorsFilter corsFilter() {
	        CorsConfiguration corsConfig = new CorsConfiguration();
	        //corsConfig.setAllowedOrigins(List.of("*"));
	        corsConfig.setAllowedOrigins(List.of("http://localhost:3000","http://164.100.137.235:8081","http://10.88.253.235:8081")); // Add your allowed origins here
	        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        corsConfig.setAllowedHeaders(List.of("*"));

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", corsConfig);

	        return new CorsFilter(source);
	    }

}
