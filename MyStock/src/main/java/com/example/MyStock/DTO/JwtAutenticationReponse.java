package com.example.MyStock.DTO;

import lombok.Data;

@Data
public class JwtAutenticationReponse {

	private String tokentype ="Bearear";
	
	private String token;
	
	public JwtAutenticationReponse(String token) {
		this.token = token;
	}
}
