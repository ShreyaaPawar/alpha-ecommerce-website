package com.shreyy.billingsoftware.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shreyy.billingsoftware.io.PaymentDetails;
import com.shreyy.billingsoftware.io.PaymentMethod;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name =  "tbl_orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderId;
	private String customerName;
	private String phoneNumber;
	private Double subtotal;
	private Double tax;
	private Double grandTotal;
	private LocalDateTime createdAt;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "order_id")
	private List<OrderItemEntity> items = new ArrayList<OrderItemEntity>();
	@Embedded
	private PaymentDetails paymentDetails;
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;

	@PrePersist
	protected void onCreate() {
		this.orderId = "ORD" + System.currentTimeMillis();
		this.createdAt = LocalDateTime.now();
	}
}
