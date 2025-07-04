package com.shreyy.billingsoftware.io;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
	
	private String itemId;
	private String name;
	private BigDecimal price;
	private String categoryId;
	private String description;
	private String categoryName;
	private String imgUrl;
	private Timestamp createdAt;
	private Timestamp updatedAt;

}
