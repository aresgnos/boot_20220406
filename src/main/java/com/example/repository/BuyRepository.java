package com.example.repository;

import java.util.List;

import com.example.entity.BuyEntity;
import com.example.entity.BuyProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository extends JpaRepository<BuyEntity, Long> {

    // findBy변수
    // findBy변수_하위변수
    // projection은 DB의 뷰(join해서 조회하는) 역할을 한다.

    BuyProjection findByBno(Long bno);

    List<BuyProjection> findByMember_uemail(String uemail);

    // SELECT * FROM 테이블명 WHERE 1 ORDER BY BNO ASC
    List<BuyProjection> findByOrderByBnoAsc();

    // SELECT * FROM 테이블명 WHERE BCNT >= ?
    List<BuyProjection> findByBcntGreaterThanEqual(long cnt);

    // SELECT * FORM 테이블명 WHERE BNO=? AND BCNT=?
    BuyProjection findByBnoAndBcnt(Long bno, Long bcnt);

    // SELECT * FROM 테이블명 WHERE BNO IN (1,3,5)
    // SELECT * FROM 테이블명 WHERE BNO 1 OR BNO=3 OR BNO=5
    List<BuyProjection> findByBnoIn(List<Long> bno);

    // native
    @Query(value = "SELECT * FROM BUY", nativeQuery = true)
    public List<BuyProjection> selectBuyList();

    @Query(value = "SELECT * FROM BUY WHERE BNO = :no", nativeQuery = true)
    public BuyProjection selectBuyOne(@Param(value = "no") long bno);

    // mybatis = #{obj.bno} === JPA + HIBERNATE = :#{#obj.bno}
    @Query(value = "SELECT * FROM BUY WHERE BNO = :#{#obj.bno} AND BCNT", nativeQuery = true)
    public BuyProjection selectBuyOneAnd(@Param(value = "obj") BuyEntity buy);

}