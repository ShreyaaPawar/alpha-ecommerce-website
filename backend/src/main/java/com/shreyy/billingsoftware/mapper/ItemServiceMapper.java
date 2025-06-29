package com.shreyy.billingsoftware.mapper;

import java.util.UUID;

import com.shreyy.billingsoftware.entity.ItemEntity;
import com.shreyy.billingsoftware.io.ItemRequest;
import com.shreyy.billingsoftware.io.ItemResponse;

public class ItemServiceMapper {

	public static ItemEntity convertToEntity(ItemRequest request) {
		return ItemEntity.builder()
			          	 .itemId(UUID.randomUUID().toString())
			             .description(request.getDescription())
			             .price(request.getPrice())
			             .name(request.getName())
			             .build();
    }

	public static ItemResponse convertToResponse(ItemEntity entity) {
		return ItemResponse.builder()
				           .itemId(entity.getItemId())
				           .name(entity.getName())
				           .description(entity.getDescription())
				           .price(entity.getPrice())
				           .imgUrl(entity.getImgUrl())
				           .categoryName(entity.getCategory().getName())
				           .categoryId(entity.getCategory().getCategoryId())
				           .createdAt(entity.getCreatedAt())
				           .updatedAt(entity.getUpdatedAt())
				           .build();
	}

}
