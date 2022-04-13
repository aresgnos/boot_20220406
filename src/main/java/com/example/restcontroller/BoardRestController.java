package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.example.dto.BoardDTO;
import com.example.entity.BoardEntity;
import com.example.mapper.BoardMapper;
import com.example.repository.BoardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 화면으로 리턴하지 않는 컨트롤러
@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    @Autowired
    BoardMapper bMapper; // mybatis
    @Autowired
    BoardRepository bRepository; // jpa

    // global.properties에 설정되어있는 값을 가져오는 것
    @Value("${board.page.count}")
    int PAGECNT;

    // 127.0.0.1:9090/ROOT/api/board/insert
    // {"btitle" : "봄", "bcontent":"벚꽃이 많이 폈다"}
    // 글쓰기
    @RequestMapping(value = "/insert", method = { RequestMethod.POST }, consumes = { MediaType.ALL_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> customerJoinPost(@RequestBody BoardDTO board) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        int ret = bMapper.insertBoardOne(board);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/delete
    // {"bno":2}
    // 삭제
    @RequestMapping(value = "/delete", method = { RequestMethod.DELETE }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> boardDeletePost(@RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        int ret = bMapper.deleteBoardOne(bno);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/update
    // {"bno":?, "btitle":"바꿀 수", "bcontent":"있을까"}
    // 수정
    @RequestMapping(value = "/update", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> boardUpdatePost(@RequestBody BoardDTO board) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        int ret = bMapper.updateBoardOne(board);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/selectone?bno=2
    // 1개 조회
    @RequestMapping(value = "/selectone", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> boardSelectOneGET(@RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        BoardDTO retBoard = bMapper.selectBoardOne(bno);
        if (retBoard != null) {
            map.put("status", 200);
            map.put("result", retBoard);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/selectlist?page=1
    // 게시판 목록(페이지네이션)
    @RequestMapping(value = "/selectlist", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> boardSelectListGET(
            @RequestParam(name = "page") int page) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        List<BoardDTO> list = bMapper.selectBoardList((page * PAGECNT) - (PAGECNT - 1), page * PAGECNT);
        if (list != null) {
            map.put("status", 200);
            map.put("result", list);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/updatehit?bno=2
    // 게시물 조회수 1증가 시킴
    @RequestMapping(value = "/updatehit", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> boardUpdateHit(@RequestParam(name = "bno") long bno) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", 0);

        int ret = bMapper.updateBoardHitOne(bno);
        if (ret == 1) {
            map.put("status", 200);
        }
        return map;
    }

    // 127.0.0.1:9090/ROOT/api/board/updatehit1?no=2
    // 게시물 조회수 1증가
    @RequestMapping(value = "/updatehit1", method = { RequestMethod.PUT }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> boardUpdate1PUT(@RequestParam(name = "no") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            BoardEntity board = bRepository.findById(no).orElse(null);
            board.setHit(board.getHit() + 1L);
            bRepository.save(board);
            map.put("status", 200);
        } catch (Exception e) {
            map.put("status", 0);
        }
        return map;
    }

    // 이전글
    @RequestMapping(value = "/handleprev", method = { RequestMethod.GET }, consumes = {
            MediaType.ALL_VALUE }, produces = {
                    MediaType.APPLICATION_JSON_VALUE })

    public Map<String, Object> handlePrevGET(@RequestParam(name = "no") long no) {
        Map<String, Object> map = new HashMap<>();
        try {
            BoardEntity board = bRepository.findTop1ByNoLessThanOrderByNoDesc(no);
            board.setNo(board.getNo());
            map.put("status", 200);

        } catch (Exception e) {
            map.put("status", 0);
        }
        return map;

    }

}
