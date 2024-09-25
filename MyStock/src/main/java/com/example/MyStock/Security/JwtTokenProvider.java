package com.example.MyStock.Security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.MyStock.Entity.UsersDetails;
import com.example.MyStock.Repository.UsersDetailsRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	@Autowired
	private UsersDetailsRepository usersDetailsRepository;
	
	public String generateToken(Authentication authentication) {
		System.out.println("Inside generateToken");
		String email = authentication.getName();
		int sessionTime = 0;
		if(email != null) {
			UsersDetails usersDetails = usersDetailsRepository.findByEmail(email);
			if(usersDetails != null) {
				sessionTime = usersDetails.getSessionExpire();
			}
		}
		
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime()+(sessionTime));
	
		String token = Jwts.builder()
                .setSubject(email) // Set the subject of the token to the email address
                .setIssuedAt(currentDate) // Set the issued date/time of the token
                .setExpiration(expireDate) // Set the expiration date/time of the token
                .signWith(SignatureAlgorithm.HS512, "JWTSecretKey") // Sign the token with HS512 algorithm and secret key
                .compact(); // Compact the token to its final String form
		return token;
	}
	
	public String getEmailFromToken(String token) {
	   Claims claims =	Jwts.parser().setSigningKey("JWTSecretKey").parseClaimsJws(token).getBody();
	   
	   return claims.getSubject();
	}
	
	public boolean validToken(String token) {
		try {
			 Jwts.parser().setSigningKey("JWTSecretKey").parseClaimsJws(token);
			 
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

}
