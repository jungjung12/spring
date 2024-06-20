package com.kh.spring.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.common.model.vo.PageInfo;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("boardlist")
	public String forwording(@RequestParam(value="page" ) int page) {
		
		//페이징 처리
		
		//RowBound 미사용
		//RowBound 사용
		
		int listCount;	// 게시판의 총 게시글 수
		int currentPage;	// 현재 페이지(사용자가 요청한 페이지) -> 앞에서 넘길 것
		int pageLimit;	// 페이지 하단에 보여질 페이징 바의 최대 갯수 -> 10개로 고정
		int boardLimit;	// 현 페이지에 보여질 게시글의 최대 개수 -> 10개로 고정
		
		int maxPage;	// 가장 마지막 페이지가 몇 번 페이지인지(페이지의 총 개수)
		int startPage;	// 페이지 하단에 보여질 페이징 바의 시작 수
		int endPage;	// 페이지 하단에 보여질 페이징 바의 마지막 수
		
		// *listCount : 총 게시글 수
		listCount = boardService.boardCount();
		
		// currentPage : 현재 페이지(사용자가 요청한 페이지)
		currentPage = page;
		
		log.info("게시글 수 : {}", listCount, currentPage);
		
		// pageLimit : 페이지 하단에 보여질 페이징 바의 최대 갯수
		pageLimit = 10;
		
		// boardLimit : 현 페이지에 보여질 게시글의 최대 개수
		boardLimit = 10;
		
		// maxPage : 가장 마지막 페이지가 몇 번 페이지인지
		/*
		 * listCount를 double로 변환 -> double 과 int 의 연산은 불가능하므로 int가 double로 형 변환이 이루어져 연산 가능
		 * 
		 * */
		maxPage = (int)Math.ceil((double)listCount/ boardLimit);
		
		// startPage 공식
		/*currentPage, pageLimit에 영향을 받음
		 * 
		 * startPage = n * pageLimit + 1
		 * 
		 * n = (currentPage - 1) / pageLimit
		 * 
		 * startPage = (currentPage - 1) * pageLimit + 1
		 * 
		 * */
		startPage = (currentPage - 1) * pageLimit + 1;
		
		// endPage 공식
		/*startPage, pageLimit에 영향을 받음(maxPage도 영향을 줌)
		 * 
		 * endPage = startPage + pageLimit - 1
		 * 
		 * */
		
		endPage = startPage + pageLimit - 1;
		
		if(endPage>maxPage) endPage = maxPage;
		/* @AllArgsConstructor만 사용
		 * PageInfo pageInfo = new
		 * PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage
		 * );
		 */
		
		//@AllArgsConstructor + @Builder 함께 사용 -> 실무에서 많이 사용하는 디자인 패턴
		PageInfo pageInfo = PageInfo.builder()
									.listCount(listCount)
									.currentPage(currentPage)
									.pageLimit(pageLimit)
									.boardLimit(boardLimit)
									.maxPage(maxPage)
									.startPage(startPage)
									.endPage(endPage)
									.build();
		
		return "board/list";
	}
}
