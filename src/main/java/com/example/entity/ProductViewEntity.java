package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;

@Immutable // view인 경우 추가, 읽기(조회)만 가능, findBy만 가능
@Entity
@Data
@Table(name = "PRODUCT_VIEW2")
public class ProductViewEntity {

    @Id
    @Column(name = "no")
    Long no;

    @Column(length = 250)
    String name;

    Long price;

    Long cnt;

}
