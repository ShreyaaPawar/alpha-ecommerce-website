package com.shreyy.billingsoftware.service.impl;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.shreyy.billingsoftware.entity.OrderEntity;
import com.shreyy.billingsoftware.entity.OrderItemEntity;
import com.shreyy.billingsoftware.io.OrderRequest;
import com.shreyy.billingsoftware.io.OrderResponse;
import com.shreyy.billingsoftware.io.PaymentDetails;
import com.shreyy.billingsoftware.io.PaymentMethod;
import com.shreyy.billingsoftware.io.PaymentVerificationRequest;
import com.shreyy.billingsoftware.repo.OrderEntityRepository;
import com.shreyy.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderEntityRepository orderEntityRepository;

	@Override
	public OrderResponse createOrder(OrderRequest request) {
		OrderEntity entity = convertToOrderEntity(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setStatus(entity.getPaymentMethod() == PaymentMethod.CASH ? 
				PaymentDetails.PaymentStatus.COMPLETED : PaymentDetails.PaymentStatus.PENDING
		);
		entity.setPaymentDetails(paymentDetails);
		List<OrderItemEntity> orderItems = request.getCartItems()
				                                  .stream()
											      .map(this :: convertToOrderItemEntity)
											      .collect(Collectors.toList());
		entity.setItems(orderItems);
		entity = orderEntityRepository.save(entity);
		return convertToResponse(entity);
	}
	
	private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
		return OrderItemEntity.builder()
				              .itemId(orderItemRequest.getItemId())
				              .name(orderItemRequest.getName())
				              .price(orderItemRequest.getPrice())
				              .quantity(orderItemRequest.getQuantity())
				              .build();
	}
	
	private OrderEntity convertToOrderEntity(OrderRequest request) {
		return OrderEntity.builder()
				          .customerName(request.getCustomerName())
				          .phoneNumber(request.getPhoneNumber())
				          .subtotal(request.getSubtotal())
				          .tax(request.getTax())
				          .grandTotal(request.getGrandTotal())
				          .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
				          .build();
	}
	
	private OrderResponse convertToResponse(OrderEntity entity) {
		return OrderResponse.builder()
				            .orderId(entity.getOrderId())
				            .customerName(entity.getCustomerName())
				            .phoneNumber(entity.getPhoneNumber())
				            .subtotal(entity.getSubtotal())
				            .tax(entity.getTax())
				            .grandTotal(entity.getGrandTotal())
				            .paymentMethod(entity.getPaymentMethod())
				            .items(entity.getItems().stream().map(this::convertToItemResponse).collect(Collectors.toList()))
				            .paymentDetails(entity.getPaymentDetails())
				            .createdAt(entity.getCreatedAt())
				            .build();
	}
	
	private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {
		return OrderResponse.OrderItemResponse.builder()
									          .itemId(orderItemEntity.getItemId())
									          .name(orderItemEntity.getName())
									          .price(orderItemEntity.getPrice())
									          .quantity(orderItemEntity.getQuantity())
									          .build();
	}

	@Override
	public void deleteOrder(String orderId) {
		OrderEntity existingOrder = orderEntityRepository.findByOrderId(orderId)
				                                         .orElseThrow(() -> new RuntimeException("Order not found"));
		orderEntityRepository.delete(existingOrder);
	}

	@Override
	public List<OrderResponse> getLatestOrders() {
		return orderEntityRepository.findAllByOrderByCreatedAtDesc()
				                    .stream()
				                    .map(this::convertToResponse)
				                    .collect(Collectors.toList());
	}

	@Override
	public OrderResponse verifyPayment(PaymentVerificationRequest request) {
		OrderEntity existingOrder = orderEntityRepository.findByOrderId(request.getOrderId()).orElseThrow(
				() -> new RuntimeException("Order not found"));
		
		if(!verifyRazorpaySignature(request.getRazorpayOrderId(),
				request.getRazorpayPaymentId(),
				request.getRazorpaySignature())) {
			throw new RuntimeException("Payment verification failed");
		}
		
		PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
		paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
		paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
		paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
		paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);
		
		existingOrder = orderEntityRepository.save(existingOrder);
		return convertToResponse(existingOrder);
		
		
	}

	private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId,
			String razorpaySignature) {
		return true;
	}
	
	@Override
	public Double sumSalesByDate(LocalDate date) {
		return orderEntityRepository.sumSalesByDate(date);
	}
	
	@Override
	public Long countByOrderDate(LocalDate date) {
		return orderEntityRepository.countByOrderDate(date);
	}
	
	@Override
	public List<OrderResponse> findRecentOrders(){
		return orderEntityRepository.findRecentOrders(PageRequest.of(0,  5))
				                    .stream()
				                    .map(orderEntity -> convertToResponse(orderEntity))
				                    .collect(Collectors.toList());
	}


}
