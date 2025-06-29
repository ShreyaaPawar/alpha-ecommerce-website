package com.shreyy.billingsoftware.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.shreyy.billingsoftware.io.ItemRequest;
import com.shreyy.billingsoftware.io.ItemResponse;

public interface ItemService {
	
	ItemResponse add(ItemRequest request, MultipartFile file) throws IOException;
	
	List<ItemResponse> fetchItem();
	
	void deleteItem(String itemId);

}
