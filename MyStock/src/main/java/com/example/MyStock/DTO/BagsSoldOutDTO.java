package com.example.MyStock.DTO;

import java.sql.Date;

import com.example.MyStock.Entity.RiceBags;

import lombok.Data;

@Data
public class BagsSoldOutDTO {

	    private Integer soldId;
	    
	    private String riceBagName;
	    
	    private Double riceQuantity;
	    
	    private Date date;
	    
	    private Date fromDate;
	    
	    private Date toDate;
	    
	    private Double grandTotalAmount;
	    
	    private Double grandTotalQuantity;
	    
	    private Double totalAmtProfit;
	    
	    private Double ourGrandTotalAmt;
	    
	     private RiceBags riceBags;
}
