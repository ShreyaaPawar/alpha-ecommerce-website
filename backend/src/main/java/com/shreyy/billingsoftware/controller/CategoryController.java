package com.shreyy.billingsoftware.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shreyy.billingsoftware.io.CategoryRequest;
import com.shreyy.billingsoftware.io.CategoryResponse;
import com.shreyy.billingsoftware.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class CategoryController {
	
	private final CategoryService service;

	@PostMapping("/admin/categories")
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryResponse addCategory(@RequestPart("category") String categoryString,
										@RequestPart("file") MultipartFile file) {
		ObjectMapper objectMapper = new ObjectMapper();
		CategoryRequest req = null;
		try {
			req = objectMapper.readValue(categoryString, CategoryRequest.class);
			return service.addCategory(req, file);
		}catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occured while parsing the JSON!");			
		}catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not able to add category!");			
		}
	}
	 
	@GetMapping("/categories")
	public List<CategoryResponse> getAllCategories(){
		return service.getAllCategories();
	}
	
	@DeleteMapping("/admin/categories/{categoryId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCategory(@PathVariable("categoryId") String categoryId) {
		try {
			service.deleteCategory(categoryId);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
}
