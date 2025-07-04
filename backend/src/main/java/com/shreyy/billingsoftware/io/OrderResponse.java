package com.shreyy.billingsoftware.io;

import java.time.LocalDateTime;
import java.util.List;

import com.shreyy.billingsoftware.io.OrderRequest.OrderItemRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
	private String orderId;
	private String customerName;
	private String phoneNumber;
	private List<OrderResponse.OrderItemResponse> items;
	private Double subtotal;
	private double tax;
	private Double grandTotal;
	private PaymentMethod paymentMethod;
	private LocalDateTime createdAt;
	private PaymentDetails paymentDetails;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class OrderItemResponse {
		private String itemId;
		private String name;
		private Double price;
		private Integer quantity;
	}

}
