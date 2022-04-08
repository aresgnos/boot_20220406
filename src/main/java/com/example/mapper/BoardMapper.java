package com.example.mapper;

import com.example.dto.BoardDTO;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BoardMapper {

        // 게시판 글쓰기
        @Insert({
                        " INSERT ",
                        " INTO BOARD(BNO, BTITLE, BCONTENT, BHIT, BREGDATE, BTYPE, UEMAIL) ",
                        " VALUES(SEQ_BOARD_NO.NEXTVAL, #{brd.btitle}, #{brd.bcontent, jdbcType=CLOB}, #{brd.bhit}, ",
                        " CURRENT_DATE, #{brd.btype}, #{brd.uemail}) "
        })
        public int insertBoardOne(@Param(value = "brd") BoardDTO board);

        // 삭제
        @Delete({
                        " DELETE FROM BOARD WHERE BNO=#{bno} "
        })
        public int deleteBoardOne(@Param(value = "bno") long bno);

        // 수정
        @Update({
                        " UPDATE ",
                        " BOARD SET BTITLE=#{brd.btitle}, BCONTENT=#{brd.bcontent} ",
                        " WHERE BNO=#{brd.bno}"
        })
        public int updateBoardOne(@Param(value = "brd") BoardDTO board);

        // 1개 조회하기
        @Select({
                        "SELECT * FROM BOARD WHERE BNO=#{bno}"
        })
        public BoardDTO selectBoardOne(@Param("bno") long bno);

        // 전체 목록 조회하기
        @Select({
                        " SELECT * FROM (",
                        " SELECT B.*, ROW_NUMBER() OVER (ORDER BY BNO DESC) ROWN ",
                        " FROM BOARD B ",
                        " ) WHERE ROWN BETWEEN #{start} AND #{end}"
        })
        public List<BoardDTO> selectBoardList(
                        @Param(value = "start") int s,
                        @Param(value = "end") int e);

        // 조회수 증가
        @Update({
                        "UPDATE BOARD SET BHIT=BHIT+1 WHERE BNO=#{bno}"
        })
        public int updateBoardHitOne(
                        @Param(value = "bno") long bno);

}
