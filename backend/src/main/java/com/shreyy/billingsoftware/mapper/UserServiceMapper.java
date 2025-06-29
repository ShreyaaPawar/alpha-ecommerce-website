package com.shreyy.billingsoftware.mapper;

import java.util.UUID;
import com.shreyy.billingsoftware.entity.UserEntity;
import com.shreyy.billingsoftware.io.UserRequest;
import com.shreyy.billingsoftware.io.UserResponse;

public class UserServiceMapper {

	public static UserEntity convertToEntity(UserRequest request, String encodedPassword) {
		return UserEntity.builder()
				          .userId(UUID.randomUUID().toString())
				          .email(request.getEmail())
				          .password(encodedPassword)
				          .role(request.getRole().toUpperCase())
				          .name(request.getName())
				          .build();
	}

	public static UserResponse convertToResponse(UserEntity newUser) {
		System.err.println("inside convert response");
		return UserResponse.builder()
				           .name(newUser.getName())
				           .userId(newUser.getUserId())
				           .email(newUser.getEmail())
				           .createdAt(newUser.getCreatedAt())
				           .updatedAt(newUser.getUpdatedAt())
				           .role(newUser.getRole())
				           .build();
	}

}
