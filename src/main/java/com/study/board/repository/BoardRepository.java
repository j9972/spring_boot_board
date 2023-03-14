package com.study.board.repository;

import com.study.board.entity.board;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

@Repository
/*
     JpaRepository<엔티티, 기본키 타입> => 이렇게 작성하는 것이다.
 */
public interface BoardRepository extends JpaRepository<board, Long> {
    /*
        Jpa Repository
        findBy(컬럼이름) -> 정확한 컬럼이름 으로 검색
        findBy(컬럼이름)Containing -> 컬럼에서 키워드가 포함된 것을 찾겠다
     */
    Page<board> findByTitleContaining(String searchKeyword, Pageable pageable);
}
