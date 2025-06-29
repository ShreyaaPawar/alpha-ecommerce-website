package com.shreyy.billingsoftware.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shreyy.billingsoftware.entity.UserEntity;
import com.shreyy.billingsoftware.io.UserRequest;
import com.shreyy.billingsoftware.io.UserResponse;
import com.shreyy.billingsoftware.mapper.UserServiceMapper;
import com.shreyy.billingsoftware.repo.UserRepository;
import com.shreyy.billingsoftware.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserResponse createUser(UserRequest request) {
		UserEntity newUser = UserServiceMapper.convertToEntity(request, passwordEncoder.encode(request.getPassword()));
		System.err.println("Converetd to newuser");
		newUser = userRepository.save(newUser);
		System.err.println("saving details");
		return UserServiceMapper.convertToResponse(newUser);
	}

	@Override
	public String getUserRole(String email) {
		UserEntity existingUser = userRepository.findByEmail(email)
		              .orElseThrow(() -> new UsernameNotFoundException("user not found for the email " + email));
		return existingUser.getRole();
	}

	@Override
	public List<UserResponse> readUser() {
		return userRepository.findAll()
				             .stream()
				             .map(user -> UserServiceMapper.convertToResponse(user))
				             .collect(Collectors.toList());
	}

	@Override
	public void deleteuser(String id) {
		UserEntity userEntity = userRepository.findByUserId(id)
				      						  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
		userRepository.delete(userEntity);
		
	}

	@Override
	public String getUsername(String email) {
		UserEntity existingUser = userRepository.findByEmail(email)
	              .orElseThrow(() -> new UsernameNotFoundException("user not found for the email " + email));
	return existingUser.getName();
	}

}
