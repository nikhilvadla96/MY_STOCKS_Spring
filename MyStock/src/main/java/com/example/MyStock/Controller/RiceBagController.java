package com.example.MyStock.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyStock.Entity.JpaResponse;
import com.example.MyStock.Service.RiceBagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import com.example.MyStock.DTO.BagsSoldOutDTO;
import com.example.MyStock.DTO.RiceBagDto;
@RestController
@RequestMapping("/ricebag")
@Tag(name = "Rice Bags Apis", description = "Api's to Save and Delete and Get the Rice bags")
public class RiceBagController {
	
	private RiceBagService riceBagService;
	
	

	@Autowired
	public void setRiceBagService(RiceBagService riceBagService) {
		this.riceBagService = riceBagService;
	}



	@GetMapping("/getAllRiceBags")
	@Operation(summary = "GET Operation" , description = "This Api Get all the details of teh rice bags")
	public ResponseEntity<Object> getAllRiceBags(){
		
		JpaResponse jpaResponse = riceBagService.getAllRiceBags();
		return new ResponseEntity<>(jpaResponse , HttpStatus.OK);
	}
	
	@PostMapping("/saveRiceBag")
	@Operation(summary = "POST Operation" , description = "This Method will add the new rice bags")
	public ResponseEntity<Object> saveRiceBag(@RequestBody  RiceBagDto riceBagDto){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE saveRiceBag METHOD");
		jpaResponse =riceBagService.saveRiceBag(riceBagDto);
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getRiceBagById/{riceBagId}")
	@Operation(summary = "GET Operation" , description = "This Api Get a particular details of the rice bags")
	public ResponseEntity<Object> getRiceBagById(@PathVariable("riceBagId") String riceBagId){
		JpaResponse jpaResponse = riceBagService.getRiceBagById(riceBagId);
		return new ResponseEntity<>(jpaResponse , HttpStatus.OK);
	}
	
	@PostMapping("/updateRiceBag")
	@Operation(summary = "POST Operation" , description = "This Method will updatet the existing  rice bags")
	public ResponseEntity<Object> updateRiceBag(@RequestBody  RiceBagDto riceBagDto){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE updateRiceBag METHOD");
		jpaResponse =riceBagService.updateRiceBag(riceBagDto);
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
	
	@PostMapping("/deleteRiceBag/{riceBagId}")
	@Operation(summary = "POST Operation" , description = "This Method will delete the  rice bags")
	public ResponseEntity<Object> deleteRiceBag(@PathVariable("riceBagId") String riceBagId){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE deleteRiceBag METHOD");
		jpaResponse =riceBagService.deleteRiceBag(riceBagId);
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
	
	@GetMapping("/fetchAllRiceBagNames")
	public ResponseEntity<Object> fetchAllRiceBagNames(){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE deleteRiceBag METHOD");
		jpaResponse =riceBagService.fetchAllRiceBagNames();
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
	
	@GetMapping("/getStockReport")
	public ResponseEntity<Object> getStockReport(BagsSoldOutDTO bagsSoldOutDTO ){
		JpaResponse jpaResponse  = new JpaResponse();
		System.out.println("INSIDE getStockReport METHOD");
		jpaResponse =riceBagService.getStockReport(bagsSoldOutDTO);
		return new ResponseEntity<Object>(jpaResponse,HttpStatus.OK);
	}
}
