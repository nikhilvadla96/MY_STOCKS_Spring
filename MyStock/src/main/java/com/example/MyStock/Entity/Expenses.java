package com.example.MyStock.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Expenses {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  int expenseId;
	
	private String expenseName;
	
	private double amount;

}
