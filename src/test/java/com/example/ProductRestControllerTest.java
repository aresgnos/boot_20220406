package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

// 수동으로 추가하기
import static org.assertj.core.api.Assertions.assertThat;

import com.example.entity.ProductCountEntity;
import com.example.entity.ProductEntity;

public class ProductRestControllerTest {

    RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        restTemplate = new RestTemplate();
    }

    // 재고 수량 조회
    @Test
    public void selectCountCount() {
        String url = "http://127.0.0.1:9090/ROOT/api/productcount/selectcount.json?no=1001";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);

        assertThat(result).contains("\"status\":200");

    }

    // @RequestBody
    // 127.0.0.1:9090/ROOT/api/productcount/insert.json
    // {cnt:12, type:"I", product:{no:1003}}
    @Test
    public void insertProductCount() {
        ProductCountEntity pcount = new ProductCountEntity();
        pcount.setCnt(5L); // 12개
        pcount.setType("I"); // 입고
        ProductEntity product = new ProductEntity();
        product.setNo(1010L); // 1003번 물품
        pcount.setProduct(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductCountEntity> entity = new HttpEntity<>(pcount, headers);

        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:9090/ROOT/api/productcount/insert.json",
                HttpMethod.POST, entity, String.class);
        System.out.println(result.getBody());

        // assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
        assertThat(result.getBody().toString()).contains("\"status\":200");
    }

    // http://127.0.0.1:9090/ROOT/api/product/insert.json
    // @ModelAttribute
    @Test
    public void insertTest() {
        // 전송하고자 하는 값 생성
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("name", "테스트용");
        body.add("price", 1234L);

        // header생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:9090/ROOT/api/product/insert.json",
                HttpMethod.POST, entity, String.class);

        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
    }

    // http://127.0.0.1:9090/ROOT/api/product/update.json
    // @ModelAttribute
    @Test
    public void updateTest() {
        // 전송하고자 하는 값 생성
        ProductEntity product = new ProductEntity();
        product.setNo(1004L);
        product.setName("가나라");
        product.setPrice(10000L);

        // header생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductEntity> entity = new HttpEntity<>(product, headers);

        ResponseEntity<String> result = restTemplate.exchange("http://127.0.0.1:9090/ROOT/api/product/update.json",
                HttpMethod.PATCH, entity, String.class);

        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
    }
}
