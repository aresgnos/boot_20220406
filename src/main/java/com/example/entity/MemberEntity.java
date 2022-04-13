package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBER")
public class MemberEntity {

    // 이메일
    @Id
    private String uemail;

    // 암호
    @Column(nullable = false)
    private String upw;

    // 이름
    private String uname;

    // 연락처
    private String uphone;

    // 권한
    private String urole;

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp // CURRENT_DATE
    @Column(name = "UREGDATE")
    private Date uregdate;

}