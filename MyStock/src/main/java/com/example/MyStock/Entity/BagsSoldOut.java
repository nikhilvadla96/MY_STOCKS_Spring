package com.example.MyStock.Entity;


import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

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

	public Integer getSoldId() {
		return soldId;
	}

	public void setSoldId(Integer soldId) {
		this.soldId = soldId;
	}

	public RiceBags getRiceBags() {
		return riceBags;
	}

	public void setRiceBags(RiceBags riceBags) {
		this.riceBags = riceBags;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getRiceSoldQuantity() {
		return riceSoldQuantity;
	}

	public void setRiceSoldQuantity(Double riceSoldQuantity) {
		this.riceSoldQuantity = riceSoldQuantity;
	}  
	
}

