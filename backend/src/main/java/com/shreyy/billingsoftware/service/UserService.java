package com.shreyy.billingsoftware.service;

import java.util.List;

import com.shreyy.billingsoftware.io.UserRequest;
import com.shreyy.billingsoftware.io.UserResponse;

public interface UserService {
	
	UserResponse createUser(UserRequest request);
	
	String getUserRole(String email);
	
	String getUsername(String email);
	
	List<UserResponse> readUser();
	
	void deleteuser(String id);

}
