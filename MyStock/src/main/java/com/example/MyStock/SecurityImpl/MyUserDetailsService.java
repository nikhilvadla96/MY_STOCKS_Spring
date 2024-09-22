package com.example.MyStock.SecurityImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.MyStock.Entity.UsersDetails;
import com.example.MyStock.Repository.UsersDetailsRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersDetailsRepository usersDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersDetails user =	usersDetailsRepository.findByEmail(username);
	    if(user == null) {
	    	System.out.println("404 not found");
	    	throw new UsernameNotFoundException("404 User not found");
	    }
		return new UserPrincipal(user);
	}

}
