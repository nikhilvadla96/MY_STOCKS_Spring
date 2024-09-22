package com.example.MyStock.SecurityImpl;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import com.example.MyStock.Entity.UsersDetails;

public class UserPrincipal implements UserDetails {
	
	private UsersDetails users;

	public UserPrincipal(UsersDetails users) {
		super();
		this.users = users;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return users.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return users.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	}
