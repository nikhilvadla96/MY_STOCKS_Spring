package com.example.MyStock.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.MyStock.Entity.UsersDetails;

public interface UsersDetailsRepository extends JpaRepository<UsersDetails, Integer> {

	UsersDetails findByEmail(String username);
	
	@Query("select sessionExpire from UsersDetails where email =:email")
	int getsSessionExpiryByEmail(@Param("email") String email);
}
