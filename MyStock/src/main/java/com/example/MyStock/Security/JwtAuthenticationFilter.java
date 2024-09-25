package com.example.MyStock.Security;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.MyStock.Repository.UsersDetailsRepository;
import com.example.MyStock.SecurityImpl.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	MyUserDetailsService detailsService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("Inside doFilterInternal");
		String token = getToken(request);
		
		if(StringUtils.hasText(token) && jwtTokenProvider.validToken(token)) {
			
			String email = jwtTokenProvider.getEmailFromToken(token);
			
			UserDetails userDetails = detailsService.loadUserByUsername(email);
			UsernamePasswordAuthenticationToken authenticationToken = new  UsernamePasswordAuthenticationToken(userDetails, null ,userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		}
		filterChain.doFilter(request, response);
		
	}
	
	private String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(StringUtils.hasText(token) && token.startsWith("Bearer ")){
			return token.substring(7, token.length());
		}
		return null;
	}

}
