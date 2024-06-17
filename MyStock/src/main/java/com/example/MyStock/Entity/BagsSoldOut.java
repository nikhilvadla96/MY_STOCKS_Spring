package com.example.MyStock.Entity;


import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class BagsSoldOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer soldId;
    
    @ManyToOne
    @JoinColumn(name = "rice_bags_id")
    private RiceBags riceBags;
    
    private Double riceSoldQuantity;
    
    private Date date;
    
    private Double pricePerKg;
	
	private Double ourPricePerKg;
	
	private Double totalSoldPrice;
	
	private Double ourTotalSoldPrice;
	
	private Double amtProfit;
	
	private Double amtProfitInPercentage;
	
}

