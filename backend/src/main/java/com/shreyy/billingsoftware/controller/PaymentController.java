package com.shreyy.billingsoftware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.shreyy.billingsoftware.io.OrderResponse;
import com.shreyy.billingsoftware.io.PaymentRequest;
import com.shreyy.billingsoftware.io.PaymentVerificationRequest;
import com.shreyy.billingsoftware.io.RazorpayOrderResponse;
import com.shreyy.billingsoftware.service.OrderService;
import com.shreyy.billingsoftware.service.RazorpayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0/payments")
@RequiredArgsConstructor
public class PaymentController {
	
	private final RazorpayService razorpayService;
	
	private final OrderService orderService;
	
	@PostMapping("/create-order")
	@ResponseStatus(HttpStatus.CREATED)
	public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException {
		return razorpayService.createOrder(request.getAmount(), request.getCurrency());
	}

	@PostMapping("/verify")
	private OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request) {
		return orderService.verifyPayment(request);
	}
	
}
