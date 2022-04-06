package com.example.mapper;

import java.util.List;

import com.example.dto.ItemDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface ItemMapper {

        // 목록
        @Select({
                        "SELECT * FROM (SELECT I.ICODE, I.INAME, I.IPRICE, I.IQUANTITY, I.ICONTENT, I.IREGDATE,",
                        "ROW_NUMBER() OVER(ORDER BY I.ICODE DESC) ROWN FROM ITEM I ",
                        "WHERE I.INAME LIKE '%' || #{txt} || '%'AND I.UEMAIL = #{email})",
                        "WHERE ROWN BETWEEN #{start} AND #{end}" })
        public List<ItemDTO> selectItemList(
                        @Param(value = "email") String email,
                        @Param(value = "txt") String txt,
                        @Param(value = "start") int start,
                        @Param(value = "end") int end);

        // 1개 조회
        @Select({
                        "SELECT ICODE, INAME, ICONTENT, IPRICE, IQUANTITY, IREGDATE",
                        "FROM ITEM",
                        "WHERE ICODE=#{icode}"
        })
        public ItemDTO selectItemOne(@Param(value = "code") long code);

        // 물품 등록
        @Insert({
                        " INSERT INTO ITEM",
                        " (ICODE, INAME, ICONTENT, IPRICE, IQUANTITY, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME, UEMAIL )",
                        " VALUES (SEQ_ITEM_ICODE.NEXTVAL, #{iname},#{icontent}, #{iprice}, #{iquantity}, #{iimage, jdbcType=BLOB}, #{iimagesize},#{iimagetype}, #{iimagename}, #{uemail} )"
        })
        public int insertItemOne(@Param(value = "obj") ItemDTO item);

        // 전체 개수(페이지네이션에 사용)
        @Select({
                        "SELECT COUNT(*) CNT",
                        "FROM ITEM I",
                        "WHERE I.INAME LIKE '%' || #{txt} || '%'",
                        "AND I.UEMAIL = #{email}"
        })
        public long selectItemCount(
                        @Param(value = "email") String email,
                        @Param(value = "txt") String txt);

        // 이미지 1개 조회
        @Results({
                        @Result(column = "ICODE", property = "icode"),
                        @Result(column = "IIMAGE", property = "iimage", jdbcType = JdbcType.BLOB, javaType = byte[].class)
        })
        @Select({
                        "SELECT ICODE, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME",
                        "FROM ITEM",
                        "WHERE ICODE = #{code}"
        })
        public ItemDTO selectItemImageOne(
                        @Param(value = "code") long code);

}
