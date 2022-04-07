package com.example.restcontroller;

import java.util.HashMap;
import java.util.Map;

import com.example.dto.BoardDTO;
import com.example.mapper.BoardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    @Autowired
    BoardMapper bMapper;

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

}
