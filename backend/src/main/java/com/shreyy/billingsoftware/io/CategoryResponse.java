package com.shreyy.billingsoftware.io;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponse {

	private String name;
	private String description;
	private String bgColor;
	private String categoryId;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private String imgUrl;
	private Integer items;
	
}
