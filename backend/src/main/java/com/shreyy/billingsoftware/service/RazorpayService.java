package com.shreyy.billingsoftware.service;

import com.razorpay.RazorpayException;
import com.shreyy.billingsoftware.io.RazorpayOrderResponse;

public interface RazorpayService {

	RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
