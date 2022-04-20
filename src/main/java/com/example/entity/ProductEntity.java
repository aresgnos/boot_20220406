package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "PRODUCT")
@SequenceGenerator(name = "SEQ3", sequenceName = "SEQ_PRODUCT_NO", allocationSize = 1, initialValue = 1001)
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "SEQ3", strategy = GenerationType.SEQUENCE)
    Long no;

    @Column(length = 250)
    String name;

    Long price;

    @Lob
    @Column(nullable = true)
    byte[] imagedata;

    String imagename;

    String imagetype;

    Long imagesize = 0L;

    // 업데이트할 때마다 시간이 바뀜
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @UpdateTimestamp
    Date update;

    @JsonBackReference // 더이상 못 들어가게(한 번만 들어가게)
    @OneToMany(mappedBy = "product") // ProductCountEntity의 반대 (1개 개념)
    List<ProductCountEntity> list = new ArrayList<>();

}
