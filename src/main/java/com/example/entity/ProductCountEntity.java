package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCTCOUNT")
@SequenceGenerator(name = "SEQ_PC", sequenceName = "SEQ_PRODUCTCOUNT_NO", allocationSize = 1, initialValue = 1)
public class ProductCountEntity {

    @Id
    @GeneratedValue(generator = "SEQ_PC", strategy = GenerationType.SEQUENCE)
    Long no;

    Long cnt; // 입고시 10, 출고시 -5

    @Column(length = 10)
    String type; // 입고는 I, 출고는 O

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    Date regdate; // 등록일

    @ManyToOne // 외래키
    @JoinColumn(name = "PRODUCT_NO", referencedColumnName = "NO")
    ProductEntity product;

}