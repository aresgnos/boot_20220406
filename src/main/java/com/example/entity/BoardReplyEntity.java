package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@Table(name = "BOARD10_REPLY") // 테이블명

// 서버 중지하고 만들어야함
// 생성할 시퀀스
@SequenceGenerator(name = "SEQ1", sequenceName = "SEQ_BOARD10_REPLY_RNO", allocationSize = 1, initialValue = 1)

public class BoardReplyEntity {

    @Id
    @Column(name = "RNO") // 기본키, 테이블명 RNO, 시퀀스사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ1")
    private long no;

    @Lob // CLOB
    @Column(name = "RCONTENT", length = 300) // VARCHAR2(300)
    private String content;

    @Column(name = "RWRITER", length = 50) // VARCHAR2(50)
    private String writer;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // CURRENT_DATE
    @Column(name = "BREGDATE")
    private Date regdate;

    @ToString.Exclude // toString이 더이상 동작되지 않게
    @ManyToOne // 외래키, DB에는 BoardEntity에 있는 내용이 다 들어가는게 아니라 기본키만 들어간다.
    @JoinColumn(name = "BOARD")
    private BoardEntity board; // BOARD10테이블의 기본키 컬럼만 들어간다.

}
