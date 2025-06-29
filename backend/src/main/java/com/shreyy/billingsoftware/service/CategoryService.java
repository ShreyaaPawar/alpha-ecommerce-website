package com.shreyy.billingsoftware.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shreyy.billingsoftware.io.CategoryRequest;
import com.shreyy.billingsoftware.io.CategoryResponse;

public interface CategoryService {
	
	CategoryResponse addCategory(CategoryRequest req, MultipartFile file) throws IOException;
	
	List<CategoryResponse> getAllCategories();
	
	void deleteCategory(String categoryId);

}
