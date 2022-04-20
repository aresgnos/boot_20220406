package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.example.entity.ProductEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiseImpl implements ProductService {
    @Autowired
    EntityManagerFactory emf;

    @Override
    public int insertBatch(List<ProductEntity> list) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (ProductEntity prd : list) {
                em.persist(prd);
            }
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return 0;
        }
    }

}
