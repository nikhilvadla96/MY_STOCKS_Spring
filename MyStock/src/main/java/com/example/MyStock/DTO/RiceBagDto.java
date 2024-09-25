package com.example.MyStock.DTO;

import lombok.Data;

@Data
public class RiceBagDto {
	
	
private int riceBagId;
	
	private String riceBagCode;
	
	private String riceBagName;
	
	private String riceBagStatus;
	
	private String riceBagDelete;
	
    private double pricePerKg;
    
    private Double ourPricePerKg;
    
    private Double stockAvailable;
    
    private Double priceperTotalAvbStock;
}
