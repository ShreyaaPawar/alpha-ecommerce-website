package com.shreyy.billingsoftware.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
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
import com.shreyy.billingsoftware.io.ItemRequest;
import com.shreyy.billingsoftware.io.ItemResponse;
import com.shreyy.billingsoftware.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/admin/items")
	public ItemResponse addItem(@RequestPart("item") String itemString,
			                    @RequestPart("file") MultipartFile file) {
		ObjectMapper objectMapper = new ObjectMapper();
		ItemRequest itemRequest = null;
		try {
			itemRequest = objectMapper.readValue(itemString, ItemRequest.class);
			return itemService.add(itemRequest, file);
		}catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occured while processing the json");
		}catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error occured while adding the item");
		}
	}
	
	@GetMapping("/items")
	public List<ItemResponse> readItem() {
		return itemService.fetchItem();
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/admin/items/{id}")
	public void deleteItem(@PathVariable("id") String itemId) {
		try {
			itemService.deleteItem(itemId);
		}catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
		}
	}

}
