package com.shreyy.billingsoftware.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.shreyy.billingsoftware.entity.CategoryEntity;
import com.shreyy.billingsoftware.io.CategoryRequest;
import com.shreyy.billingsoftware.io.CategoryResponse;
import com.shreyy.billingsoftware.mapper.CategoryServiceMapper;
import com.shreyy.billingsoftware.repo.CategoryRepository;
import com.shreyy.billingsoftware.repo.ItemRepository;
import com.shreyy.billingsoftware.service.CategoryService;
import com.shreyy.billingsoftware.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository repo;
	
	private final FileUploadService fileUploadService;
	
	private final ItemRepository itemRepository;

	@Override
	public CategoryResponse addCategory(CategoryRequest req, MultipartFile file) throws IOException {
		//Uploading into AWS S3 logic
		//String imgUrl = fileUploadService.uploadFile(file);
		
		String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Files.createDirectories(uploadPath);
		Path targetLocation = uploadPath.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String imgUrl = "http://localhost:8080/api/v1.0/uploads/" + fileName;
		CategoryEntity newCategory = CategoryServiceMapper.convertToEntity(req);
		newCategory.setImgUrl(imgUrl);
		newCategory = repo.save(newCategory);
		return CategoryServiceMapper.convertToResponse(newCategory, itemRepository.countByCategoryId(newCategory.getId()));
	}
	
	@Override
	public List<CategoryResponse> getAllCategories(){
		return repo.findAll()
				   .stream()
				   .map(entity -> CategoryServiceMapper.convertToResponse(entity, itemRepository.countByCategoryId(entity.getId())))
				   .collect(Collectors.toList());
				
	}

	@Override
	public void deleteCategory(String categoryId) {
		CategoryEntity category = repo.findByCategoryId(categoryId)
		    .orElseThrow(() -> new RuntimeException("Category not found: "+ categoryId));
		//Deleting from AWS S3
		/**if(category.getImgUrl() != null)
			fileUploadService.deleteFile(category.getImgUrl());**/
		
		String imgUrl = category.getImgUrl();
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Path filePath = uploadPath.resolve(fileName);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		repo.delete(category);	
	}

}
