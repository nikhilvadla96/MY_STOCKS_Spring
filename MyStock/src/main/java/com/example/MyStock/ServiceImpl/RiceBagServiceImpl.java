package com.example.MyStock.ServiceImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyStock.DTO.BagsSoldOutDTO;
import com.example.MyStock.DTO.DropDownDto;
import com.example.MyStock.DTO.RiceBagDto;
import com.example.MyStock.Entity.JpaResponse;
import com.example.MyStock.Entity.RiceBags;
import com.example.MyStock.Repository.RiceBagRepository;
import com.example.MyStock.Service.RiceBagService;


@Service
public class RiceBagServiceImpl implements RiceBagService {
	
	@Autowired
	private RiceBagRepository riceBagRepository;

	@Override
	public JpaResponse getAllRiceBags() {
		JpaResponse jpaResponse = new JpaResponse();
		try {
			List<RiceBags> listOfRiceBags =  riceBagRepository.findAllRiceBags();
			System.out.println(listOfRiceBags);
			List<Object> resultList = new ArrayList<>(listOfRiceBags);
			jpaResponse.setResultList( resultList);
			
		} catch (Exception e) {
		System.err.println(e);		
		}
	
		return jpaResponse;
	}

	@Override
	public JpaResponse saveRiceBag(RiceBagDto riceBagDto) {
		JpaResponse jpaResponse = new JpaResponse();
		List<RiceBags> riceBagsList = new ArrayList<>();
		try {
			if(riceBagDto.getRiceBagName() != null && !riceBagDto.getRiceBagName().isEmpty()) {
				
				if(riceBagDto.getRiceBagCode() != null && riceBagDto.getRiceBagName() != null) {
					riceBagsList =  	riceBagRepository.findByRiceBagCodeandRiceBagName(riceBagDto.getRiceBagCode(),riceBagDto.getRiceBagName());
				}else if(riceBagDto.getRiceBagCode() != null) {
					riceBagsList =  	riceBagRepository.findByRiceBagCode(riceBagDto.getRiceBagCode());
				}else if(riceBagDto.getRiceBagName() != null) {
					riceBagsList =  	riceBagRepository.findByRiceBagName(riceBagDto.getRiceBagName());
				}
				if(riceBagsList.size() > 0) {
					 jpaResponse.setReturnMsg("Allready exist Data");
					 return jpaResponse;
					
				}
				RiceBags riceBags = new RiceBags();
				riceBags.setRiceBagCode(riceBagDto.getRiceBagCode());
				riceBags.setRiceBagName(riceBagDto.getRiceBagName());
				riceBags.setPricePerKg(riceBagDto.getPricePerKg());
				//riceBags.setRiceBagStatus(riceBagDto.getRiceBagStatus());
				riceBags.setRiceBagDelete(riceBagDto.getRiceBagDelete());
				riceBags.setOurPricePerKg(riceBagDto.getOurPricePerKg());
				riceBags.setStockAvailable(riceBagDto.getStockAvailable());
				RiceBags riceBags1 = riceBagRepository.saveAndFlush(riceBags);
				if(riceBags1.getRiceBagId() > 0) {
					jpaResponse.setReturnMsg("Rice Bag saved sussesfully");
				}else {
					jpaResponse.setReturnMsg("Rice Bag Name Required");
				}
				
			}
//			
		}catch (Exception e) {
			System.err.println(e);		
			}
		
		return jpaResponse;
	}

	@Override
	public JpaResponse getRiceBagById(String riceBagId) {
		Integer id = Integer.parseInt(riceBagId);
		JpaResponse jpaResponse = new JpaResponse();
		List<Object> riceBagsList = new ArrayList<Object>();
		RiceBagDto riceBagDto = new RiceBagDto();
		try {
			Optional<RiceBags> optional = riceBagRepository.findById(id);

			if (optional.isPresent()) {
				RiceBags riceBags = optional.get();
				riceBagDto.setRiceBagId(riceBags.getRiceBagId());
				riceBagDto.setRiceBagCode(riceBags.getRiceBagCode());
				riceBagDto.setRiceBagName(riceBags.getRiceBagName());
				riceBagDto.setPricePerKg(riceBags.getPricePerKg());
				riceBagDto.setOurPricePerKg(riceBags.getOurPricePerKg());
				riceBagDto.setStockAvailable(riceBags.getStockAvailable());
				riceBagsList.add(riceBagDto);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		jpaResponse.setResults(riceBagsList.get(0));
		return jpaResponse;
	}

	@Override
	public JpaResponse updateRiceBag(RiceBagDto riceBagDto) {
		JpaResponse jpaResponse = new JpaResponse();
		List<RiceBags> riceBagsList = new ArrayList<>();
		try {
			if(riceBagDto.getRiceBagName() != null && !riceBagDto.getRiceBagName().isEmpty() ){
				riceBagsList = riceBagRepository.findByRiceBagName(riceBagDto.getRiceBagName());
			}
			if(riceBagsList.size() > 1) {
				 jpaResponse.setReturnMsg("Allready exist Data");
				 return jpaResponse;
				
			}
			Optional<RiceBags> optRiceBag = riceBagRepository.findById(riceBagDto.getRiceBagId());
			if(optRiceBag.isPresent()) {
				RiceBags riceBags = optRiceBag.get();
				riceBags.setRiceBagCode(riceBagDto.getRiceBagCode());
				riceBags.setRiceBagName(riceBagDto.getRiceBagName());
				riceBags.setPricePerKg(riceBagDto.getPricePerKg());
				riceBags.setOurPricePerKg(riceBagDto.getOurPricePerKg());
				riceBags.setStockAvailable(riceBagDto.getStockAvailable());
				//riceBags.setRiceBagStatus(riceBagDto.getRiceBagStatus());
				riceBags.setRiceBagDelete(riceBagDto.getRiceBagDelete());
				RiceBags riceBags1 = riceBagRepository.saveAndFlush(riceBags);
				if(riceBags1.getRiceBagId() > 0) {
					jpaResponse.setReturnMsg("Rice Bag saved sussesfully");
				}
			}
			
			
//			
		}catch (Exception e) {
			System.err.println(e);		
			}
		
		return jpaResponse;
	}

	@Override
	public JpaResponse deleteRiceBag(String riceBagId) {
		JpaResponse jpaResponse = new JpaResponse();
		 int  id = Integer.parseInt(riceBagId);
		try {
			Optional<RiceBags> optRiceBag = riceBagRepository.findById(id);
			if(optRiceBag.isPresent()) {
				RiceBags riceBags = optRiceBag.get();
				riceBags.setRiceBagDelete("1");
				riceBagRepository.saveAndFlush(riceBags);
				jpaResponse.setReturnMsg("Rice Bag Deleted SussesFully");
			}else {
				jpaResponse.setReturnMsg("Deletion Failed");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jpaResponse;
	}

	@Override
	public JpaResponse fetchAllRiceBagNames() {
		JpaResponse jpaResponse = new JpaResponse();
		DropDownDto dropDownDto = new DropDownDto();
		List<DropDownDto> listOfRiceBagsNames = new ArrayList<DropDownDto>();
		List<Object[]> listOfAllRiceBag = new ArrayList<Object[]>();
		try {
			listOfAllRiceBag = riceBagRepository.fetchAllRiceBagNames();
			if(listOfAllRiceBag !=  null && !listOfAllRiceBag.isEmpty()) {
				for(Object object[] : listOfAllRiceBag) {
					 dropDownDto = new DropDownDto();
					 
					dropDownDto.setValue(object[0].toString());
					dropDownDto.setLabel(object[1].toString());
					
					listOfRiceBagsNames.add(dropDownDto);
				}
			}
			List<Object> resultList = new ArrayList<>(listOfRiceBagsNames);
			jpaResponse.setResultList(resultList);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jpaResponse;
	}
	
	@Override
	public JpaResponse getStockReport(BagsSoldOutDTO bagsSoldOutDTO) {

		JpaResponse jpaResponse = new JpaResponse();
		List<BagsSoldOutDTO> listOfStocks = new ArrayList<>();;
		int  riceBagId =0;
		RiceBags riceBags  = new RiceBags();
		Date date = null;
		if(bagsSoldOutDTO.getRiceBagName() != null && bagsSoldOutDTO.getRiceBagName() != "") {
			 riceBagId = Integer.parseInt(bagsSoldOutDTO.getRiceBagName());
		}	
		if(bagsSoldOutDTO.getDate() != null) {
			date = bagsSoldOutDTO.getDate();
		}
		try {
			if(riceBagId > 0) {
				
			}else {
				if(date!= null) {
					
					
					
				}else {
					List<RiceBags>   getAllRiceBagsList = riceBagRepository.findAllRiceBags();
					if(getAllRiceBagsList != null && !getAllRiceBagsList.isEmpty()) {
						for(int i =  0 ;i<getAllRiceBagsList.size() ;i++) {
							riceBags = getAllRiceBagsList.get(i);
							
						}
					}
					List<Object> resultList = new ArrayList<>(getAllRiceBagsList);
					jpaResponse.setResultList( resultList);
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return jpaResponse;
	}

}
