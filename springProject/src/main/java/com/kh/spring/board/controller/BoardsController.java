package com.kh.spring.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//이 컨트롤러는 /boards 로 시작하는 요청이 들어오면 처리해줄 친구
//@RestController : 해당 어노테이션이 붙은 클래스에 기술된 메소드는 ResponseBody 속성을 띔~!
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value="boards", produces="application/json; charset=UTF-8")
public class BoardsController {

	private final BoardService boardService;
	
	@GetMapping
	public List<Board> findTopFiveBoard() {
		
		return boardService.findTopFiveBoard();
	}
	
	//@PathVariable -> 요소 중 boardNo를 매개로 받아야 하기 때문에 PathVariable 을 이용해 숫자를 빼옴
	@GetMapping("/{boardNo}")
	public Board findByBoardNo(@PathVariable int boardNo) {
		// log.info("번호 : {}", boardNo);
		
		return boardService.findById(boardNo);
	}
	
}
