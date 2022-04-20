package com.example.repository;

import com.example.entity.ProductViewEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductViewEntity, Long> {

}
