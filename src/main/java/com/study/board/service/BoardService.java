package com.study.board.service;

import com.study.board.entity.board;
import com.study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
/*
 service는 controller에서 이용한다
 */
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 작성 처리
    public void write(board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 파일 이름 생성
        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        boardRepository.save(board); // save(entity) 를 넣어준다
    }

    // 게시글 리스트 처리
    public Page<board> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable);
    }


    public Page<board> boardSearchList(String searchKeyword, Pageable pageable) {

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public board boardView(Long id) {

        /*
            findById 는 optional을 받아오므로, get() 필요하다
            findById() 에는 id가 필요하다.
         */
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }
}
