package com.example.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.entity.ProductEntity;
import com.example.entity.ProductViewEntity;
import com.example.repository.ProductRepository;
import com.example.repository.ProductViewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*") // 앱으로 만들 경우 앱에서 데이터를 가져갈 수 없음.
@RequestMapping(value = "/api/product")
public class ProductRestController {

    @Autowired
    ProductRepository pRepository;

    @Autowired
    ProductViewRepository pvRepository;

    // 127.0.0.1:9090/ROOT/api/product/selectone.json?no=1001
    @GetMapping(value = "/selectone.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> selectOnePOST(
            @RequestParam(name = "no") Long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            ProductViewEntity entity = pvRepository.findById(no).orElse(null);
            map.put("result", entity);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 이미지가 있는 것을 form-data로 보내겠다고 정의함
    // 127.0.0.1:9090/ROOT/api/product/insert.json
    @PostMapping(value = "/insert.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertPOST(
            @ModelAttribute ProductEntity product,
            @RequestParam(name = "file", required = false) MultipartFile file) { // required = 이미지 첨부는 의무가 아니다.
        Map<String, Object> map = new HashMap<>();
        try {

            if (file != null) {
                if (!file.isEmpty()) {
                    product.setImagedata(file.getBytes());
                    product.setImagename(file.getOriginalFilename());
                    product.setImagesize(file.getSize());
                    product.setImagetype(file.getContentType());
                }
            }

            pRepository.save(product);
            map.put("status", 200);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    // 일괄 등록
    @PostMapping(value = "/insertbatch.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> insertBatchPOST(
            @RequestParam(name = "name") String[] name,
            @RequestParam(name = "price") String[] price,
            @RequestParam(name = "file", required = false) MultipartFile[] file) {

        Map<String, Object> map = new HashMap<>();
        try {
            List<ProductEntity> list = new ArrayList<>();

            pRepository.saveAll(list);

            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

    @PutMapping(value = "/update.json", consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public Map<String, Object> updatePatch(
            @RequestBody ProductEntity product) {
        System.out.println(product.toString());
        Map<String, Object> map = new HashMap<>();
        try {
            // 1개 꺼내기
            ProductEntity product1 = pRepository.findById(product.getNo()).orElse(null);
            // 필요정보 변경
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            pRepository.save(product1);
            map.put("status", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
        }
        return map;
    }

}