package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.entity.ProductCountEntity;
import com.example.repository.ProductCountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/productcount")
public class ProductCountRestController {

    @Autowired
    ProductCountRepository pcRepository;

    // 127.0.0.1:9090/ROOT/api/productcount/insert.json
    // {cnt:12, type:"I", product:{no:1001}}
    // {cnt:-5, type:"O", product:{no:1001}} => er다이어그램이랑 맞춰야함
    @PostMapping(value = "/insert.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @RequestBody ProductCountEntity productCount) {
        Map<String, Object> map = new HashMap<>();
        try {
            pcRepository.save(productCount);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/productcount/selectcount.json?no=1001
    @GetMapping(value = "/selectcount.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectcountPOST(
            @RequestParam(name = "no") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            Long total = pcRepository.selectProductCountGroup(no);
            map.put("status", 200);
            map.put("total", total);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

}
