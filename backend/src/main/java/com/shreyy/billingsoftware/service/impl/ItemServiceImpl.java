package com.shreyy.billingsoftware.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.shreyy.billingsoftware.entity.CategoryEntity;
import com.shreyy.billingsoftware.entity.ItemEntity;
import com.shreyy.billingsoftware.io.ItemRequest;
import com.shreyy.billingsoftware.io.ItemResponse;
import com.shreyy.billingsoftware.mapper.ItemServiceMapper;
import com.shreyy.billingsoftware.repo.CategoryRepository;
import com.shreyy.billingsoftware.repo.ItemRepository;
import com.shreyy.billingsoftware.service.FileUploadService;
import com.shreyy.billingsoftware.service.ItemService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
	
	private final ItemRepository itemRepository;
	
	private final FileUploadService fileUploadService;
	
	private final CategoryRepository categoryRepository;

	@Override
	public ItemResponse add(ItemRequest request, MultipartFile file) throws IOException {
		//Uploading into AWS S3
		//String imgUrl = fileUploadService.uploadFile(file);
		
		String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Files.createDirectories(uploadPath);
		Path targetLocation = uploadPath.resolve(fileName);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		String imgUrl = "http://localhost:8080/api/v1.0/uploads/" + fileName;
		ItemEntity entity = ItemServiceMapper.convertToEntity(request);
		CategoryEntity existingCategory = categoryRepository.findByCategoryId(request.getCategoryId())
		                  									.orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));
		entity.setCategory(existingCategory);
		entity.setImgUrl(imgUrl);
		entity = itemRepository.save(entity);
		return ItemServiceMapper.convertToResponse(entity);
	}

	@Override
	public List<ItemResponse> fetchItem() {
		return itemRepository.findAll()
		              .stream()
		              .map(itemEntity -> ItemServiceMapper.convertToResponse(itemEntity))
		              .collect(Collectors.toList());
	}

	@Override
	public void deleteItem(String itemId) {
		ItemEntity itemEntity = itemRepository.findByItemId(itemId)
				                              .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));
		//Deleting from AWS S3 
		//boolean isFileDelete = fileUploadService.deleteFile(itemEntity.getImgUrl());
		//if (isFileDelete) itemRepository.delete(itemEntity);	
		
		String imgUrl = itemEntity.getImgUrl();
		String fileName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
		Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
		Path filePath = uploadPath.resolve(fileName);
		try {
			Files.deleteIfExists(filePath);
			itemRepository.delete(itemEntity);	
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to delete the image");
		}	
	}

}
