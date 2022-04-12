package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.BoardEntity;
import com.example.entity.BoardReplyEntity;
import com.example.repository.BoardReplyRepository;
import com.example.repository.BoardRepository;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    BoardRepository bRepository;

    @Autowired
    BoardReplyRepository brRepository;

    @Value("${board.page.count}")
    int PAGECNT;

    @PostMapping(value = "/insertreply")
    public String insertReply(
            @ModelAttribute BoardReplyEntity reply) {
        System.out.println(reply.toString());
        brRepository.save(reply);
        return "redirect:/board/selectone?no="
                + reply.getBoard().getNo();
    }

    @GetMapping(value = "/insert")
    public String insertGET() {
        return "board_insert";
    }

    @PostMapping(value = "/insert")
    public String insertPOST(@ModelAttribute BoardEntity board) {

        // save(enetity객체) == INSERT INTO 테이블..
        bRepository.save(board);
        return "redirect:/board/selectlist";
    }

    // 127.0.0.1:9090/ROOT/board/selectlist?page=1&title=
    @GetMapping(value = "/selectlist")
    public String selectlistGET(
            Model model,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "title", defaultValue = "") String title) {

        // 페이지네이션(시작페이지(0부터), 개수)
        PageRequest pageRequest = PageRequest.of(page - 1, PAGECNT);

        System.out.println("title : " + title);

        // 검색어, 페이지네이션
        List<BoardEntity> list = bRepository.findByTitleContainingOrderByNoDesc(title, pageRequest);
        model.addAttribute("list", list);

        // 10=>1, 23=>3, 45=>5
        long total = bRepository.countByTitleContaining(title);
        model.addAttribute("pages", (total - 1) / PAGECNT + 1);

        return "board_selectlist";
    }

    // 127.0.0.1:9090/ROOT/board/selectone?no=11
    @GetMapping(value = "/selectone")
    public String selectOneGET(
            Model model,
            @RequestParam(name = "no") long no) {

        BoardEntity board = bRepository.findById(no).orElse(null);
        model.addAttribute("brd", board);

        System.out.println(board.getReplyList().toString());

        List<BoardReplyEntity> repList = brRepository.findByBoard_noOrderByNoDesc(board.getNo());
        model.addAttribute("repList", repList);

        return "board_selectone";

    }
}
