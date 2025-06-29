package com.shreyy.billingsoftware.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.shreyy.billingsoftware.io.UserRequest;
import com.shreyy.billingsoftware.io.UserResponse;
import com.shreyy.billingsoftware.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1.0/admin")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserResponse registerUser(@RequestBody UserRequest request) {
		try {
			return userService.createUser(request);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create user");
		}
	}
	
	@GetMapping("/users")
	public List<UserResponse> readUser() {
		return userService.readUser();
	}
	
	@DeleteMapping("/users/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") String userId) {
		try {
			userService.deleteuser(userId);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
		}
	}

}
