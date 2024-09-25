package com.example.MyStock.Entity;

import java.util.List;

import lombok.Data;

@Data
public class JpaResponse {

	List<Object> resultList;

	String returnMsg;

	Object results;
	
	boolean isValid = false;

}
