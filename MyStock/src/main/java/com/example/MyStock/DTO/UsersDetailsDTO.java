package com.example.MyStock.DTO;

import lombok.Data;

@Data
public class UsersDetailsDTO {

   private String email;
	
   private String password;
   
   private int expiredHours;
   
   private int expiredMinutes;
}
