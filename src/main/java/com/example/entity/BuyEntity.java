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
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Data
@Table(name = "BUY")
@SequenceGenerator(name = "SEQ2", sequenceName = "SEQ_BUY_NO", allocationSize = 1, initialValue = 1)
public class BuyEntity {

    @Id // 기본키
    @Column(name = "BNO") // 컬럼명
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ2") // 시퀀스 적용
    private Long bno; // 타입 변수

    @Column(name = "BCNT")
    private Long bcnt;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // CURRENT_DATE
    @Column(name = "BREGDATE")
    private Date bregdate;

    // 물품테이블
    @ManyToOne
    @JoinColumn(name = "icode")
    private ItemEntity item;

    // 회원테이블
    @ManyToOne
    @JoinColumn(name = "uemail")
    private MemberEntity member;

}
