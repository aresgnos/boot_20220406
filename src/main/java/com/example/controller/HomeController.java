package com.example.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.ItemDTO;
import com.example.mapper.ItemMapper;

@Controller
public class HomeController {

    @Autowired
    ResourceLoader resLoader;
    @Autowired
    ItemMapper iMapper;
    @Value("${default.image}")
    String DEFAULT_IMAGE;

    // 통합해서 build
    // 127.0.0.1:9090/ROOT/vue
    @GetMapping(value = "/vue")
    public String vueGET() {
        // vue에서 build한 index.html파일의 위치
        return "/vue/index";
    }

    // 127.0.0.1:9090/ROOT/
    // 127.0.0.1:9090/ROOT/home
    // 127.0.0.1:9090/ROOT/main
    @GetMapping(value = { "/", "/home", "/main" })
    public String homeGET(
            Model model,
            @AuthenticationPrincipal User user) { // Detailservice에서 만든걸 꺼냄
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping(value = "/page403")
    public String page403GET() {
        return "page403";
    }

    // 127.0.0.1:9090/ROOT/item/image?code=21
    // <img th:src="@{/item/image(code=1)}" style="width:100px" />
    @GetMapping(value = "/item/image")
    public ResponseEntity<byte[]> imageGET(
            @RequestParam(name = "code") long code)
            throws IOException {
        // 이미지명, 이미지크기, 이미지종류, 이미지데이터
        ItemDTO item = iMapper.selectItemImageOne(code);
        System.out.println(item.getIimagetype());
        System.out.println(item.getIimage().length);

        if (item.getIimagesize() > 0) {
            HttpHeaders headers = new HttpHeaders();
            if (item.getIimagetype().equals("image/jpeg")) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else if (item.getIimagetype().equals("image/png")) {
                headers.setContentType(MediaType.IMAGE_PNG);
            } else if (item.getIimagetype().equals("image/gif")) {
                headers.setContentType(MediaType.IMAGE_GIF);
            }
            ResponseEntity<byte[]> response = new ResponseEntity<>(item.getIimage(), headers, HttpStatus.OK);
            return response;
        } else {
            InputStream is = resLoader.getResource(DEFAULT_IMAGE).getInputStream();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            ResponseEntity<byte[]> response = new ResponseEntity<>(is.readAllBytes(), headers, HttpStatus.OK);
            return response;
        }
    }

}
