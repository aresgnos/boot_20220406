package com.example.mapper;

import com.example.dto.MemberDTO;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MemberMapper {

        // 회원 탈퇴(일부 데이터 지우기)
        @Update({
                        "UPDATE MEMBER",
                        "SET UNAME=NULL, UPHONE=NULL, UROLE=NULL",
                        "WHERE UEMAIL=#{obj.uemail}"
        })
        public int deleteMemberOne(@Param(value = "obj") MemberDTO member);

        // 회원정보수정
        @Update({
                        "UPDATE",
                        "MEMBER SET UNAME=#{obj.uname}, UPHONE=#{obj.uphone}",
                        "WHERE UEMAIL=#{obj.uemail}"
        })
        public int updateMemberOne(@Param(value = "obj") MemberDTO member);

        // 암호 변경
        @Update({
                        "UPDATE",
                        "MEMBER SET UPW=#{pw}",
                        "WHERE UEMAIL=#{email}"
        })
        public int updatePw(@Param(value = "email") String email,
                        @Param(value = "pw") String pw);

        @Select({
                        "SELECT UEMAIL, UPW, UROLE, UPHONE, UNAME FROM MEMBER",
                        "WHERE UEMAIL =#{email}"
        })
        public MemberDTO memberEmail(
                        @Param(value = "email") String em);

        // 로그인
        // SELECT 컬럼명들 FROM 테이블명 WHERE 조건 AND 조건
        // #{value 이름이 들어감}
        @Select({
                        "SELECT UEMAIL, UNAME, UROLE FROM MEMBER",
                        "WHERE UEMAIL =#{email} AND UPW=#{pw}"
        })
        public MemberDTO memberLogin(
                        @Param(value = "email") String em,
                        @Param(value = "pw") String userpw);

        // 회원가입
        // INSERT INTO 테이블명(컬럼명들... ) VALUES(추가할 값들...)
        // 공백까지 생각해서
        @Insert({
                        " INSERT INTO MEMBER",
                        " (UEMAIL, UPW, UNAME, UPHONE, UROLE, UREGDATE) ",
                        " VALUES(#{obj.uemail}, #{obj.upw}, #{obj.uname}, #{obj.uphone}, #{obj.urole}, CURRENT_DATE) "
        })
        public int memberJoin(
                        @Param(value = "obj") MemberDTO member);

}
