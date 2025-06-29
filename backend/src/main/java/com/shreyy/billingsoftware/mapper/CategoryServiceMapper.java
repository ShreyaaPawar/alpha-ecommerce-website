package com.shreyy.billingsoftware.mapper;

import java.util.UUID;

import com.shreyy.billingsoftware.entity.CategoryEntity;
import com.shreyy.billingsoftware.io.CategoryRequest;
import com.shreyy.billingsoftware.io.CategoryResponse;
import com.shreyy.billingsoftware.repo.ItemRepository;

public class CategoryServiceMapper {
	
	public static CategoryEntity convertToEntity(CategoryRequest req) {
		return CategoryEntity.builder()
					  .categoryId(UUID.randomUUID().toString())
					  .name(req.getName())
					  .description(req.getDescription())
					  .bgColor(req.getBgColor())
					  .build();
	}
	
	public static CategoryResponse convertToResponse(CategoryEntity req, Integer itemsCount) {
		return CategoryResponse.builder()
					  .categoryId(req.getCategoryId())
					  .name(req.getName())
					  .description(req.getDescription())
					  .bgColor(req.getBgColor())
					  .createdAt(req.getCreatedAt())
					  .imgUrl(req.getImgUrl())
					  .updatedAt(req.getUpdatedAt())
					  .items(itemsCount)
					  .build();
	}

}
