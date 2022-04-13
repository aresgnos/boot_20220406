package com.example.entity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

// 필요한 항목만 가져오기
public interface BuyProjection {

    // 주문번호
    Long getBno();

    // 주문수량
    Long getBcnt();

    // 주문일자
    Date getBregdate();

    // 물품명
    @Value("#{target.item.iname}")
    String getItemIname();

    // 물품 가격
    @Value("#{target.item.iprice}")
    Long getItemIprice();

    // 회원이름
    @Value("#{target.member.uname}")
    String getMemberUname();

    // 연락처
    @Value("#{target.member.uphone}")
    String getMemberUphone();
}
