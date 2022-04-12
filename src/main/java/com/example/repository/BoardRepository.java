package com.example.repository;

import java.util.List;

import com.example.entity.BoardEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
// JPA 저장소에 있는 것들을 상속받음
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

        // 참고
        // https://docs.spring.io/spring-data/jpa/docs/2.6.3/reference/html/#jpa.sample-app.finders.strategies
        // 네이티브도 쓸 수 있다.

        // 검색어가 포함된 전체 개수
        // SELECT COUNT(*) FROM BOARD10 WHERE
        // WHERE BTITLE LIKE '%' || '검색어' || '%'
        long countByTitleContaining(String title);

        // findBy컬럼명Containing
        // SELECT * FROM BOARD10
        // WHERE BTITLE LIKE '%' || '검색어' || '%'
        List<BoardEntity> findByTitleContaining(String title);

        // findBy컬럼명ContainingOrderBy컬럼명Desc
        // SELECT * FROM BOARD10
        // WHERE BTITLE LIKE '%' || '검색어' || '%' ORDER BY BNO DESC
        List<BoardEntity> findByTitleContainingOrderByNoDesc(String title);

        // SELECT B.*, ROW_NUMBER() OVER( ORDER BY BNO DESC ) FROM BOARD10 B
        // WHERE BTITLE LIKE '%' || '검색어' || '%'
        List<BoardEntity> findByTitleContainingOrderByNoDesc(String title,
                        Pageable pageable);

        // 이전글 ex) 20일 때 작은 것 중에서 가장 큰 값 = 19
        BoardEntity findTop1ByNoLessThanOrderByNoDesc(long no);

        // 다음글 ex) 20일 때 큰 것 중에서 가장 작은 것 1개 = 21
        BoardEntity findTop1ByNoGreaterThanOrderByNoDesc(long no);

        @Query(value = " SELECT * FROM BOARD10 B WHERE BTITLE LIKE %:ti% ", nativeQuery = true)
        List<BoardEntity> selectBoardList(
                        @Param(value = "ti") String title);

}
