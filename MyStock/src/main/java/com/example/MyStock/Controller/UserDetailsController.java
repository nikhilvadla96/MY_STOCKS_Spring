package com.example.MyStock.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyStock.DTO.JwtAutenticationReponse;
import com.example.MyStock.DTO.UsersDetailsDTO;
import com.example.MyStock.Entity.JpaResponse;
import com.example.MyStock.Entity.UsersDetails;
import com.example.MyStock.Security.JwtTokenProvider;
import com.example.MyStock.Service.UsersDetailsService;


@RestController
@RequestMapping("/auth")
public class UserDetailsController {
	
	private boolean  isValid = false;

	@Autowired
	private UsersDetailsService usersDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UsersDetailsDTO usersDetailsDTO) {
		System.out.println("Inside registerUser method");
		String result = usersDetailsService.registerUser(usersDetailsDTO);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAutenticationReponse> login(@RequestBody UsersDetailsDTO usersDetailsDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(usersDetailsDTO.getEmail(), usersDetailsDTO.getPassword()));
		if (authentication == null) {
			// return new ResponseEntity<String>("User not Found" , HttpStatus.OK);
		}
		System.out.println(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ResponseEntity.ok(new JwtAutenticationReponse(token));
	}
	
	@GetMapping("/verifyToken") 
	public ResponseEntity<Object> verifyToken(){
		JpaResponse jpaResponse = new JpaResponse();
		jpaResponse.setValid(true);
		 return new ResponseEntity<>(jpaResponse, HttpStatus.OK);
	}
	
	@PostMapping("/updateUsersDetails")
	public ResponseEntity<String> updateUsersDetails(@RequestBody UsersDetailsDTO usersDetailsDTO ){
		
		return new ResponseEntity<String>( usersDetailsService.updateUsersDetails(usersDetailsDTO) , HttpStatus.OK);
		
	}
	
	@GetMapping("/getUserInfo/{email}") 
	public ResponseEntity<Object> getUserInfo(@PathVariable("email") String email){
		JpaResponse jpaResponse = new JpaResponse();
		jpaResponse = usersDetailsService.getUserInfo(email);
		return new ResponseEntity<Object>( jpaResponse , HttpStatus.OK);
	}
}
