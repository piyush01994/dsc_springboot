package com.dsc.dsclife.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {
	
	
	  public static final long JWT_TOKEN_VALIDITY = 10*60*60;
	  public String secret = "test1234567898765432123456789jdhsajdhalkdhaslkdjhasdas32123456789jdnasjdhasjkdhasldjasdhasdhasldas876543320921234567898765433213abcsderftghyujikloppiuytresw";
	  
	  public String getUsernameFromToken(String token) {
	        return getClaimFromToken(token, Claims::getSubject);
	    }
	 
	  
	  public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	    //for retrieveing any information from token we will need the secret key
	    public Claims getAllClaimsFromToken(String token) {
	    	//return Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
	    	
	        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	    }

	    //check if the token has expired
	    private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }

	    //generate token for user
	    public String generateToken(UserDetails userDetails,String usertype) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("usertype",usertype);
	        claims.put("userid",userDetails.getUsername());
	        return doGenerateToken(claims, userDetails.getUsername());
	    }
	  
	   
	    @SuppressWarnings("deprecation")
		private String doGenerateToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	        
	        
	    }

	    //validate token
	    public Boolean validateToken(String token, UserDetails userDetails) {
	    	
	        final String userid = getUsernameFromToken(token);
	       if(isTokenExpired(token)) {
	    	
	       }
	        return (userid.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	    
	    
	    // Function to generate a new token with updated claim
	    public String generateTokenWithUpdatedClaim(String oldToken, String healthinstitutecode) {
	        final Claims claims = getAllClaimsFromToken(oldToken);
	        claims.put("healthinstitutecode", healthinstitutecode);

	        return Jwts.builder()
	                .setClaims(claims)
	                .signWith(SignatureAlgorithm.HS256, "secretKey")
	                .compact();
	    }

}
