package com.shreyy.billingsoftware.io;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
	
	private String customerName;
	private String phoneNumber;
	private List<OrderRequest.OrderItemRequest> cartItems;
	private Double subtotal;
	private double tax;
	private Double grandTotal;
	private String paymentMethod;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class OrderItemRequest {
		private String itemId;
		private String name;
		private Double price;
		private Integer quantity;
	}
	

}
