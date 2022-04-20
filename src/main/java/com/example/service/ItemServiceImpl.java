package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.example.entity.ItemEntity;
import com.example.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    ItemRepository iRepository;

    // 일괄 등록
    @Override
    public int insertItemBatch(List<ItemEntity> list) {
        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            // 여러개 추가
            for (ItemEntity item : list) {
                em.persist(item); // save
            }
            em.getTransaction().commit();
            // em.getTransaction().rollback();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 일괄 수정
    @Override
    public int updateItemBatch(List<ItemEntity> list) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // 여러개 추가
            for (ItemEntity item : list) {
                // 기본키를 이용해서 기존 데이터를 꺼내서 oldItem에 보관
                ItemEntity oldItem = em.find(ItemEntity.class, item.getIcode());

                // 변경할 항목 set
                oldItem.setIname((item.getIname()));
                oldItem.setIcontent(item.getIcontent());
                oldItem.setIprice(item.getIprice());
                oldItem.setIquantity(item.getIquantity());
                em.persist(oldItem);
            }
            em.getTransaction().commit();
            // em.getTransaction().rollback();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    // 일괄 삭제
    @Override
    public int deleteItemBatch(Long[] no) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (Long tmp : no) {
                // 기본키를 이용해서 기존 데이터를 꺼냄
                ItemEntity oldItem = em.find(ItemEntity.class, tmp);
                em.remove(oldItem);
            }
            em.getTransaction().commit();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return 0;
        }
    }

    // 해당하는 항목 조회
    @Override
    public List<ItemEntity> selectItemEntityIn(Long[] no) {
        return iRepository.findByIcodeIn(no);
    }

}
