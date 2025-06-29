package com.shreyy.billingsoftware.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shreyy.billingsoftware.io.DashboardResponse;
import com.shreyy.billingsoftware.io.OrderResponse;
import com.shreyy.billingsoftware.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0/dashboard")
@RequiredArgsConstructor
public class DashboardController {
	
	private final OrderService orderService;
	
	@GetMapping
	public DashboardResponse getDashboardData() {
		LocalDate today = LocalDate.now();
		Double todaySale = orderService.sumSalesByDate(today);
		Long todayOrderCount = orderService.countByOrderDate(today);
		List<OrderResponse> recentOrders = orderService.findRecentOrders();
		return new DashboardResponse(
				todaySale != null ? todaySale : 0.0,
			    todayOrderCount != null ? todayOrderCount : 0,
			    recentOrders
		);
	}

}
