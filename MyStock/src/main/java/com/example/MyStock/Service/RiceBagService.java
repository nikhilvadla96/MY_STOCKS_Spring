package com.example.MyStock.Service;

import com.example.MyStock.DTO.BagsSoldOutDTO;
import com.example.MyStock.DTO.RiceBagDto;
import com.example.MyStock.Entity.JpaResponse;

public interface RiceBagService {

	public JpaResponse getAllRiceBags();
	
	public JpaResponse saveRiceBag(RiceBagDto riceBagDto);

	public JpaResponse getRiceBagById(String riceBagId);
	
	public JpaResponse updateRiceBag(RiceBagDto riceBagDto);
	
	public JpaResponse deleteRiceBag(String riceBagId);

	public JpaResponse fetchAllRiceBagNames();

	public JpaResponse getStockReport(BagsSoldOutDTO bagsSoldOutDTO);
}
