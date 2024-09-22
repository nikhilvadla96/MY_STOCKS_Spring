package com.example.MyStock.ServiceImpl;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.example.MyStock.DTO.UsersDetailsDTO;
import com.example.MyStock.Entity.JpaResponse;
import com.example.MyStock.Entity.UsersDetails;
import com.example.MyStock.Repository.UsersDetailsRepository;
import com.example.MyStock.Service.UsersDetailsService;

@Service
public class UsersDetailsServiceImpl implements UsersDetailsService {

	@Autowired
	private UsersDetailsRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public String registerUser(UsersDetailsDTO usersDetailsDTO) {
		
		String password = encoder.encode(usersDetailsDTO.getPassword());
		
		UsersDetails usersDetails = new UsersDetails();
		usersDetails.setEmail(usersDetailsDTO.getEmail());
		usersDetails.setPassword(password);
		return repository.saveAndFlush(usersDetails).getEmail() + " Saved Sussesfully";
	}

	@Override
	public String updateUsersDetails(UsersDetailsDTO usersDetailsDTO) {
		JpaResponse jpaResponse = new JpaResponse();
		String email = usersDetailsDTO.getEmail();
		int  sessionExpire =0;
		
		UsersDetails details =  repository.findByEmail(email);
		if(details != null) {
			if(StringUtils.trimToNull(usersDetailsDTO.getPassword()) != null) {
				String password = encoder.encode(usersDetailsDTO.getPassword());
				details.setPassword(password);
			}
			if(usersDetailsDTO.getExpiredHours() > 0 || usersDetailsDTO.getExpiredMinutes() >0) {
				int expiredHours = usersDetailsDTO.getExpiredHours();
				int expiredMinutes = usersDetailsDTO.getExpiredMinutes();
				if(expiredHours > 0) {
					sessionExpire = sessionExpire+=(expiredHours*60*60*1000);
				}
				if(expiredMinutes >0) {
					sessionExpire = sessionExpire+=(expiredMinutes*60*1000);
				}
				details.setSessionExpire(sessionExpire);
			}else {
				sessionExpire = sessionExpire+=(60*60*1000);
				details.setSessionExpire(sessionExpire);
			}
			repository.saveAndFlush(details);
			jpaResponse.setReturnMsg("User Updated Sussesfully");
			
			RestTemplate restTemplate = new RestTemplate();
			String responseEntity = restTemplate.getForObject("http://localhost:7050/api/millsectoHrs/"+sessionExpire, String.class);
			return "User Updated Sussesfully";	
		}else {
			return "Incorrect UserName/Email";
		}
		
		// TODO Auto-generated method stub
	}

	@Override
	public JpaResponse getUserInfo(String email) {
		
		int sessionExpiry = repository.getsSessionExpiryByEmail(email);
		
		
		return millsectoHrs(sessionExpiry+"");
	}
	
	public JpaResponse millsectoHrs(@PathVariable("milliseconds") String milliseconds) {
		JpaResponse jpaResponse = new JpaResponse();
		UsersDetailsDTO usersDetailsDTO = new UsersDetailsDTO();
		// Convert milliseconds to Duration
        Duration duration = Duration.ofMillis(Long.parseLong(milliseconds));
        
        // Extract hours, minutes, and seconds
        int hours = (int)duration.toHours();
        int minutes = (int)duration.toMinutes() % 60;
        int seconds = (int) duration.get(ChronoUnit.SECONDS) % 60;
        
        usersDetailsDTO.setExpiredHours( (hours));
        usersDetailsDTO.setExpiredMinutes(minutes);
        jpaResponse.setResults(usersDetailsDTO);
        
        
        return jpaResponse;
    }

}
