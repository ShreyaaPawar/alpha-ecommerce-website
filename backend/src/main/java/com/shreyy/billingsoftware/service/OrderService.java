package com.shreyy.billingsoftware.service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import com.shreyy.billingsoftware.io.OrderRequest;
import com.shreyy.billingsoftware.io.OrderResponse;
import com.shreyy.billingsoftware.io.PaymentVerificationRequest;

public interface OrderService {
	
	OrderResponse createOrder(OrderRequest request);
	
	void deleteOrder(String orderId);
	
	List<OrderResponse> getLatestOrders();

	OrderResponse verifyPayment(PaymentVerificationRequest request);
	
	Double sumSalesByDate(LocalDate date);
	
	Long countByOrderDate(LocalDate date);
	
	List<OrderResponse> findRecentOrders();

}
