package com.example.controller;

import java.util.List;

import com.example.dto.ItemDTO;
import com.example.mapper.ItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/seller")
public class SellerController {

    @Autowired
    ItemMapper iMapper;

    // int PAGECNT = 10
    // global.properties에 설정되어있는 값을 가져오는 것
    @Value("${board.page.count}")
    int PAGECNT;

    // 127.0.0.1:9090/ROOT/seller
    // 127.0.0.1:9090/ROOT/seller/home

    @GetMapping(value = { "/", "/home" })
    public String sellerGET(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "txt", defaultValue = "") String txt,
            @AuthenticationPrincipal User user) { // 로그인 됐는지 안됐는지
        if (user != null) {

            // 1일 때 -> 1, 10
            // 2일 때 -> 11, 20

            // 1 -> 1,5
            // 2 -> 6, 10
            // 목록
            List<ItemDTO> list = iMapper.selectItemList(user.getUsername(), txt, (page * PAGECNT) - (PAGECNT - 1),
                    page * PAGECNT);
            model.addAttribute("list", list);

            // 페이지네이션 개수
            long cnt = iMapper.selectItemCount(user.getUsername(), txt);
            model.addAttribute("pages", (cnt - 1) / PAGECNT + 1);
            return "seller";
        }
        return "redirect:/member/login";
    }
}
