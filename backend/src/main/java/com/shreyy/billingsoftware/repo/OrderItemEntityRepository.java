package com.shreyy.billingsoftware.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreyy.billingsoftware.entity.OrderItemEntity;

public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity, Long>{

}
