package com.example.MyStock.ServiceImpl;




import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.MyStock.DTO.BagsSoldOutDTO;
import com.example.MyStock.Entity.BagsSoldOut;
import com.example.MyStock.Entity.JpaResponse;
import com.example.MyStock.Entity.RiceBags;
import com.example.MyStock.Repository.BagsSoldOutRepository;
import com.example.MyStock.Repository.RiceBagRepository;
import com.example.MyStock.Service.BagsSoldOutService;

@Service
public class BagsSoldOutServiceImpl implements BagsSoldOutService {
	
	@Autowired
	private BagsSoldOutRepository bagsSoldOutRepository;
	
	@Autowired
	private RiceBagRepository riceBagRepository;

	@Override
	public JpaResponse saveBagsSoldOut(BagsSoldOutDTO bagsSoldOutDTO) {
		
		JpaResponse jpaResponse = new JpaResponse();
		try {
			if(bagsSoldOutDTO.getRiceBagName() != null &&  !bagsSoldOutDTO.getRiceBagName().isEmpty() ) {
				
				int riceBagId = Integer.parseInt(bagsSoldOutDTO.getRiceBagName());
				RiceBags riceBags = riceBagRepository.findById(riceBagId).get();
				riceBags.setStockAvailable((riceBags.getStockAvailable() -bagsSoldOutDTO.getRiceQuantity()));
				
				BagsSoldOut bagsSoldOut = new BagsSoldOut();
				bagsSoldOut.setRiceBags(riceBags);
				bagsSoldOut.setRiceSoldQuantity(bagsSoldOutDTO.getRiceQuantity() );
				bagsSoldOut.setDate(new java.sql.Date(System.currentTimeMillis()));
				
				bagsSoldOut.setPricePerKg(riceBags.getPricePerKg());
				bagsSoldOut.setOurPricePerKg(riceBags.getOurPricePerKg());
				double ricesQuantity = bagsSoldOutDTO.getRiceQuantity();
				double totalPrice = ricesQuantity * riceBags.getPricePerKg();
				double ourTotPrice = ricesQuantity * riceBags.getOurPricePerKg();
				Double amtprofitPerc = 0.00;
				if(ricesQuantity == 25 || ricesQuantity ==26) {
					bagsSoldOut.setTotalSoldPrice(totalPrice-ricesQuantity);
					bagsSoldOut.setAmtProfit((totalPrice-ricesQuantity)-ourTotPrice);
					 amtprofitPerc = ((((totalPrice-ricesQuantity)-ourTotPrice)/(totalPrice-ricesQuantity))*100);
				}else {
					bagsSoldOut.setTotalSoldPrice(totalPrice);
					bagsSoldOut.setAmtProfit(totalPrice-ourTotPrice);
					 amtprofitPerc = (((totalPrice-ourTotPrice)/totalPrice)*100);
				}
				bagsSoldOut.setOurTotalSoldPrice(ourTotPrice);
				
				 DecimalFormat df = new DecimalFormat("#.##");
				bagsSoldOut.setAmtProfitInPercentage(Double.parseDouble(df.format(amtprofitPerc)));
				
				bagsSoldOutRepository.saveAndFlush(bagsSoldOut);
				jpaResponse.setReturnMsg(riceBags.getRiceBagName() +" Sold Out");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return jpaResponse;
	}

	public JpaResponse getRiceBagsSoldOut(BagsSoldOutDTO bagsSoldOutDTO ){
		
		JpaResponse jpaResponse = new JpaResponse();
		int  riceBagId =0;
		Date fromDate = new Date(System.currentTimeMillis());
		Date toDate = new Date(System.currentTimeMillis());
		if(bagsSoldOutDTO.getRiceBagName() != null && bagsSoldOutDTO.getRiceBagName() != "") {
			 riceBagId = Integer.parseInt(bagsSoldOutDTO.getRiceBagName());
		}	
		if(bagsSoldOutDTO.getFromDate() != null && bagsSoldOutDTO.getToDate() != null) {
			fromDate = bagsSoldOutDTO.getFromDate();
			toDate =bagsSoldOutDTO.getToDate();
		}else if(bagsSoldOutDTO.getFromDate() != null) {
			fromDate = bagsSoldOutDTO.getFromDate();
		}else if(bagsSoldOutDTO.getToDate() != null) {
			toDate =bagsSoldOutDTO.getToDate();
		}
	    try {
	    	if(riceBagId > 0) {
	    		List<BagsSoldOut> listOfBagsSoldOut =  bagsSoldOutRepository.getRiceBagsSoldOut(riceBagId ,fromDate , toDate);
		    	 
		    	 List<Object[]> getTotalQuantityAndPrice = bagsSoldOutRepository.getTotalQuantityAndPrice(riceBagId , fromDate , toDate);
		    	 
		    	 for(Object [] objects : getTotalQuantityAndPrice) {
		    		 double amount = (double) objects[1];
		    		 double quantity = (double) objects[0];
		    		 double ourTotal = (double) objects[2];
		    		 
		    		 bagsSoldOutDTO = new BagsSoldOutDTO();
		    		 bagsSoldOutDTO.setGrandTotalAmount(amount);
		    		 bagsSoldOutDTO.setGrandTotalQuantity(quantity);
		    		 bagsSoldOutDTO.setOurGrandTotalAmt(ourTotal);
		    		 bagsSoldOutDTO.setTotalAmtProfit(amount-ourTotal);
		    	 }
		    	 
		    	 jpaResponse.setResults(bagsSoldOutDTO);
		    	 
		    	 List<Object> resultList = new ArrayList<>(listOfBagsSoldOut);
					jpaResponse.setResultList( resultList);
	    	}else {
	    		List<Integer> riceBagsIds = riceBagRepository.getAllRiceBagId();
	    		List<Integer> soldRiceBagsIds = new ArrayList<>();
	    		
	    		List<BagsSoldOutDTO> listOfBagsSoldOut = new ArrayList<>();
	    		List<Object[]> getTotalQuantityAndPrice =  bagsSoldOutRepository.getAllRiceBagsSoldOut(fromDate , toDate);
	    		
	    		double grandTotalQuantity = 0.0;
	    		double grandTotalAmt =0.0;
	    		double grandTotalOurAmt =0.0;
	    		double grandTotalAmtProfit =0.0;
	    		
	    		
	    		for(Object [] objects : getTotalQuantityAndPrice) {
	    			 RiceBags riceBagName = (RiceBags) objects[0];
	    			 Date date = (Date) objects[1];
		    		 double amount = (double) objects[3];
		    		 double quantity = (double) objects[2];
		    		 double ourTotal = (double) objects[4];
		    		 
		    		 grandTotalQuantity+=quantity;
		    		 grandTotalAmt+=amount;
		    		 grandTotalOurAmt+=ourTotal;
		    		 grandTotalAmtProfit+=(amount-ourTotal);
		    		 
		    		 bagsSoldOutDTO = new BagsSoldOutDTO();
		    		 bagsSoldOutDTO.setRiceBags(riceBagName);
		    		 bagsSoldOutDTO.setDate(date);
		    		 bagsSoldOutDTO.setGrandTotalAmount(amount);
		    		 bagsSoldOutDTO.setGrandTotalQuantity(quantity);
		    		 bagsSoldOutDTO.setOurGrandTotalAmt(ourTotal);
		    		 bagsSoldOutDTO.setTotalAmtProfit(amount-ourTotal);
		    		 listOfBagsSoldOut.add(bagsSoldOutDTO);
		    		 soldRiceBagsIds.add(riceBagName.getRiceBagId());
		    	 }
	    		
	    		Set<Integer> set2 = new HashSet<>(soldRiceBagsIds);
	    		;
	            
	            // Create a new list to store elements from list1 that are not in list2
	            List<Integer> newList = new ArrayList<>();
	            for (Integer num : riceBagsIds) {
	                if (!set2.contains(num)) {
	                    newList.add(num);
	                }
	            }

	            if(newList != null && !newList.isEmpty()) {
	            	
	            	for(Integer id : newList) {
	            		Optional<RiceBags> optional = riceBagRepository.findById(id);
	            		if(optional.isPresent()) {
	            			RiceBags riceBags = optional.get();
	            			 bagsSoldOutDTO = new BagsSoldOutDTO();
	    		    		 bagsSoldOutDTO.setRiceBags(riceBags);
	    		    		 bagsSoldOutDTO.setDate(null);
	    		    		 bagsSoldOutDTO.setGrandTotalAmount(0.00);
	    		    		 bagsSoldOutDTO.setGrandTotalQuantity(0.00);
	    		    		 bagsSoldOutDTO.setOurGrandTotalAmt(0.00);
	    		    		 bagsSoldOutDTO.setTotalAmtProfit(0.00);
	    		    		 listOfBagsSoldOut.add(bagsSoldOutDTO);
	            		}
	            	}
	            }
	    		
	    		 List<Object> resultList = new ArrayList<>(listOfBagsSoldOut);
					jpaResponse.setResultList( resultList);
					
					bagsSoldOutDTO = new BagsSoldOutDTO();
		    		 bagsSoldOutDTO.setGrandTotalAmount(grandTotalAmt);
		    		 bagsSoldOutDTO.setGrandTotalQuantity(grandTotalQuantity);
		    		 bagsSoldOutDTO.setOurGrandTotalAmt(grandTotalOurAmt);
		    		 bagsSoldOutDTO.setTotalAmtProfit(grandTotalAmtProfit);
		    		 
		    		 jpaResponse.setResults(bagsSoldOutDTO);
	    	}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	    
	    return jpaResponse;
	}

}
