package com.example.mapper;

import java.util.List;

import com.example.dto.ItemImageDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ItemImageMapper {

    // 이미지 목록
    @Select({
            " SELECT IMGCODE FROM ITEMIMAGE ",
            " WHERE ICODE = #{icode}"
    })
    public List<Long> selectItemImageList(@Param(value = "icode") long code);

    // 이미지 한개 조회
    @Select({
            " SELECT * FROM ITEMIMAGE WHERE IMGCODE=#{imgcode} "
    })
    public ItemImageDTO selectItemImageOne(@Param(value = "imgcode") long code);

    // 이미지 수정
    @Update({
            " UPDATE ITEMIMAGE SET ",
            " IIMAGESIZE = #{obj.iimagesize} ",
            " IIMAGETYPE = #{obj.iimagetype} ",
            " IIMAGENAME = #{obj.iimagename} ",
            " IIMAGE = #{obj.iimage, jdbcType=BLOB} ",
            " WHERE IMGCODE=#{obj.imgcode} "
    })
    public int updateItemImageOne(@Param(value = "obj") ItemImageDTO obj);
    // DTO로 오기 때문에 obj

    // 이미지 일괄 등록
    @Insert({
            "<script>",
            " INSERT ALL ",
            " <foreach collection='list' item='obj' separator=' '>",
            " INTO ITEMIMAGE (IMGCODE, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME, ICODE) ",
            " VALUES ( #{obj.imgcode}, #{obj.iimage, jdbcType=BLOB}, #{obj.iimagesize}, #{obj.iimagetype}, #{obj.iimagename}, #{obj.icode} ) ",
            " </foreach> ",
            " SELECT * FROM DUAL ",
            "</script>"
    })
    public int insertItemImageBatch(@Param(value = "list") List<ItemImageDTO> list);

    // 이미지 등록
    @Insert({
            " INSERT ",
            " INTO ITEMIMAGE (IMGCODE, IIMAGE, IIMAGESIZE, IIMAGETYPE, IIMAGENAME, ICODE) ",
            " VALUES ( #{imgcode}, #{iimage, jdbcType=BLOB}, #{iimagesize}, #{iimagetype}, #{iimagename}, #{icode} ) "
    })
    public int insertItemImageOne(@Param(value = "obj") ItemImageDTO itemimg);

}
