package com.example.repository;

import java.util.List;

import com.example.entity.BoardReplyEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardReplyRepository
                extends JpaRepository<BoardReplyEntity, Long> {

        // List<BoardReplyEntity> replyList
        // Board_no = 하나의 변수라고 보면 된다.=> Board 밑에 있는 no
        // 원본 글번호가 일치하는 댓글 개수
        List<BoardReplyEntity> findByBoard_noOrderByNoDesc(long bno);
}
