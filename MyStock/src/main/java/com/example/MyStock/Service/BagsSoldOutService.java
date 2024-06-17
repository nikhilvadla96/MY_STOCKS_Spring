package com.example.MyStock.Service;


import com.example.MyStock.DTO.BagsSoldOutDTO;
import com.example.MyStock.Entity.JpaResponse;

public interface BagsSoldOutService {

	public JpaResponse saveBagsSoldOut(BagsSoldOutDTO bagsSoldOutDTO);
	
	public JpaResponse getRiceBagsSoldOut(BagsSoldOutDTO bagsSoldOutDTO );

}
