package com.example.MyStock.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyStock.DTO.BagsSoldOutDTO;
import com.example.MyStock.Entity.JpaResponse;
import com.example.MyStock.Service.BagsSoldOutService;

@RestController
@RequestMapping("/sale")
public class BagsSoldOutController {
	
	@Autowired
	private BagsSoldOutService bagsSoldOutService;
	
	@PostMapping("/saveBagsSoldOut")
	public ResponseEntity<Object> saveBagsSoldOut(@RequestBody BagsSoldOutDTO bagsSoldOutDTO ){
		
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE saveBagsSoldOut METHOD");
		jpaResponse =bagsSoldOutService.saveBagsSoldOut(bagsSoldOutDTO);
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getRiceBagsSoldOut/")
	public ResponseEntity<Object> getRiceBagsSoldOut(BagsSoldOutDTO bagsSoldOutDTO ){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE getRiceBagsSoldOut METHOD");
		jpaResponse =bagsSoldOutService.getRiceBagsSoldOut(bagsSoldOutDTO);
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}	
	
	@GetMapping("/getTotalRiceBagsPricePerDay")
	public ResponseEntity<Object> getTotalRiceBagsPricePerDay(){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE getTotalRiceBagsPricePerDay METHOD");
		jpaResponse =bagsSoldOutService.getTotalRiceBagsPricePerDay();
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
	
	@GetMapping(path = "/getEachRiceBagsDetails")
	public ResponseEntity<Object> getEachRiceBagsDetails(){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE getEachRiceBagsDetails METHOD");
		jpaResponse =bagsSoldOutService.getEachRiceBagsDetails();
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
}
