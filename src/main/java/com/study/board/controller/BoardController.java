package com.study.board.controller;

import com.study.board.entity.board;
import com.study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
// @RestController = @Controller + @ResponseBody
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home() { // localhost:2020/
        return "home";
    }

    @GetMapping("/board/write") // localhost:2020/board/write
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro") // localhost:2020/board/writepro
    /*
        parameter : String title, String content 대신 board board
     */
    public String boardWritePro(board board, Model model, MultipartFile file) throws Exception{

        boardService.write(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        // redirect:/board/list 로 안하고 글 작성 완료시 board/list로 보내기 전에 메세지 띄워줌
        return "message";
    }

    /*
        DESC -> 최신순으로 내용 정렬
     */
    @GetMapping("/board/list")  // localhost:2020/board/list
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            String searchKeyword) {

        Page<board> list = null;

        if(searchKeyword == null) {
            list = boardService.boardList(pageable);
        }else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; // pageable은 0부터 시작하므로 +1 필수
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }


    @GetMapping("/board/view") // localhost:2020/board/view?id=1
    public String boardView(Model model, Long id) {

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/modify/{id}")// localhost:2020/board/modify?id=1
    public String boardModify(@PathVariable("id") Long id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @GetMapping("/board/delete")// localhost:2020/board/delete
    public String boardDelete(Model model,Long id) {

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @PostMapping("/board/update/{id}") // localhost:2020/board/update?id=1
    public String boardUpdate(@PathVariable("id") Long id, board board, MultipartFile file) throws Exception{

        board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContext(board.getContext());

        boardService.write(boardTemp, file);

        return "redirect:/board/list";

    }
}
