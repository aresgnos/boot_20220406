package com.example.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardDTO {
    // 글번호
    private Long bno;
    // 제목
    private String btitle;
    // 내용
    private String bcontent;
    // 조회수
    private Long bhit = 1L;
    // 등록일
    private Date bregdate;
    // 종류
    private Long btype = 1L;
    // 이메일
    private String uemail = null;
}
