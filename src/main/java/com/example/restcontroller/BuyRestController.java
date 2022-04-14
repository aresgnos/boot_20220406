package com.example.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.BuyEntity;
import com.example.entity.BuyProjection;
import com.example.entity.ItemEntity;
import com.example.entity.MemberEntity;
import com.example.jwt.JwtUtil;
import com.example.repository.BuyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/buy")
public class BuyRestController {

        @Autowired
        BuyRepository bRepository;

        @Autowired
        JwtUtil jwtUtil;

        // { bcnt:2, item:{icode: 3} } + headers token으로 전송됨
        @RequestMapping(value = "/insert2", method = { RequestMethod.POST }, consumes = {
                        MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
        public Map<String, Object> buyInsert2Post(
                        @RequestHeader(name = "token") String token,
                        @RequestBody BuyEntity buyEntity) {
                Map<String, Object> map = new HashMap<String, Object>();
                try {
                        // 토큰에서 이메일 추출
                        String email = jwtUtil.extractUsername(token);

                        // 회원 entity 객체 생성 및 이메일 추가
                        MemberEntity memberEntity = new MemberEntity();
                        memberEntity.setUemail(email);

                        // 주문 entity에 추가
                        buyEntity.setMember(memberEntity);

                        // 저장소를 이용해서 DB에 추가
                        bRepository.save(buyEntity);
                        map.put("status", 200);
                } catch (Exception e) {
                        e.printStackTrace();
                        map.put("status", 0);
                }
                return map;
        }

        // 주문하기
        // 127.0.0.1:9090/ROOT/api/buy/insert
        // { bcnt:2, icode: 3, uemail:'b3'}
        @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = {
                        MediaType.ALL_VALUE }, produces = {
                                        MediaType.APPLICATION_JSON_VALUE })
        public Map<String, Object> buyInsertPost(
                        @RequestBody Map<String, Object> buy) {
                System.out.println(buy.toString());

                BuyEntity buyEntity = new BuyEntity();
                buyEntity.setBcnt(Long.parseLong(buy.get("bcnt").toString()));

                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setIcode(
                                Long.parseLong(buy.get("icode").toString()));
                buyEntity.setItem(itemEntity);

                MemberEntity memberEntity = new MemberEntity();
                memberEntity.setUemail((String) buy.get("uemail"));
                buyEntity.setMember(memberEntity);

                bRepository.save(buyEntity);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", 200);
                return map;
        }

        // { bcnt:2, item:{icode: 3}, member:{uemail:'b3'}}
        @RequestMapping(value = "/insert1", method = { RequestMethod.POST }, consumes = {
                        MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
        public Map<String, Object> buyInsertPost(
                        @RequestBody BuyEntity buyEntity) {
                System.out.println(buyEntity.toString());
                Map<String, Object> map = new HashMap<String, Object>();
                bRepository.save(buyEntity);
                map.put("status", 200);
                return map;
        }

        // 주문 1개 조회
        // 127.0.0.1:9090/ROOT/api/buy/selectone?bno=8
        @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
                        MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
        public Map<String, Object> buyInsertGET(
                        @RequestParam(name = "bno") long bno) {
                Map<String, Object> map = new HashMap<String, Object>();

                // BuyEntity buyEntity = bRepository.findById(8L).orElse(null);
                BuyProjection buy = bRepository.findByBno(bno);
                map.put("result", buy);
                map.put("status", 200);
                return map;
        }

        // 주문 목록
        // 127.0.0.1:9090/ROOT/api/buy/selectlist?uemail=
        @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
                        MediaType.ALL_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
        public Map<String, Object> buyListGET(
                        @RequestParam(name = "uemail") String uemail) {
                Map<String, Object> map = new HashMap<String, Object>();

                List<BuyProjection> list = bRepository.findByMember_uemail(uemail);
                System.out.println(list.toString());
                map.put("result", list);
                map.put("status", 200);
                return map;
        }

}
