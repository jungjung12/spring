package com.kh.spring.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

	private final NoticeService noticeService;
	
	@GetMapping("noticeList")
	public String noticeList(@RequestParam(value="page", defaultValue="1") int page, Model model) {
		
		//페이징 처리
		
		int listCount;	// 게시판의 총 게시글 수
		int currentPage;	// 현재 페이지(사용자가 요청한 페이지) -> 앞에서 넘길 것
		int pageLimit;	// 페이지 하단에 보여질 페이징 바의 최대 갯수 -> 10개로 고정
		int boardLimit;	// 현 페이지에 보여질 게시글의 최대 개수 -> 10개로 고정
		
		int maxPage;	// 가장 마지막 페이지가 몇 번 페이지인지(페이지의 총 개수)
		int startPage;	// 페이지 하단에 보여질 페이징 바의 시작 수
		int endPage;	// 페이지 하단에 보여질 페이징 바의 마지막 수
		
		// *listCount : 총 게시글 수
		listCount = noticeService.noticeCount();
		
		// currentPage : 현재 페이지(사용자가 요청한 페이지)
		currentPage = page;
		
		// log.info("게시글 수 : {}", listCount, currentPage);
		
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
		
		startPage = (currentPage - 1) * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		
		PageInfo pageInfo = PageInfo.builder()
				.listCount(listCount)
				.currentPage(currentPage)
				.pageLimit(pageLimit)
				.boardLimit(boardLimit)
				.maxPage(maxPage)
				.startPage(startPage)
				.endPage(endPage)
				.build();

		Map<String, Integer> map = new HashMap();
		
		int startValue = (currentPage - 1) * pageLimit + 1;
		int endValue = startPage + pageLimit - 1;
		
		map.put("startValue", startValue);
		map.put("endValue", endValue);
		
		List<Notice> noticeList = noticeService.noticeAll(map);
		
		model.addAttribute("list", noticeList);
		model.addAttribute("pageInfo", pageInfo);
		
		return "notice/list";
	}
	
	@GetMapping("noticeForm")
	public String noticeForwording() {
		
		return "notice/insertForm";
	}
	
	@PostMapping("insertNotice")
	public String insert(Notice notice, HttpSession session) {
		
		//log.info("입력한 정보 : {}", notice);
		
		if(noticeService.insert(notice) > 0) {
			
			session.setAttribute("alertMsg", "추가 성공");
			
			return "redirect:noticeList";
			
		} else {
			
			session.setAttribute("errorMsg", "추가 실패");
			
			
			return "common/errorPage";
		}
	}
	
	
	
	@GetMapping("noticeUpdateForm")
	public String noticeUpdateForm() {
		
		return "notice/updateForm";
		
	}
}