package com.example.MyStock.Service;

import com.example.MyStock.DTO.UsersDetailsDTO;
import com.example.MyStock.Entity.JpaResponse;

public interface UsersDetailsService {

	public String registerUser(UsersDetailsDTO usersDetailsDTO);
	
	public String updateUsersDetails(UsersDetailsDTO usersDetailsDTO );

	public JpaResponse getUserInfo(String email);
}


