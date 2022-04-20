package com.example.service;

import java.util.List;

import com.example.entity.ProductEntity;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    public int insertBatch(List<ProductEntity> list);

}
