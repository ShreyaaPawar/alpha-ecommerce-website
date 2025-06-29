package com.shreyy.billingsoftware.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shreyy.billingsoftware.entity.ItemEntity;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long>{
	
	Optional<ItemEntity> findByItemId(String id);
	
	Integer countByCategoryId(Long id);

}
