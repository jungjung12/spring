package com.kh.spring.notice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.notice.model.service.NoticeService;
import com.kh.spring.notice.model.vo.Message;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
	
	/*
	 * Mapping 값 notice로 통일
	 * 
	 * C : INSERT -> @PostMapping
	 * R : SELECT -> @GetMapping
	 * U : UPDATE -> @PutMapping
	 * D : DELETE -> @DeleteMapping
	 * 
	 * REST 방식 --> 
	 * 
	 */
	
	private final NoticeService noticeService;
	
	//ResponseEntity : 응답용 객체를 생성하는 클래스
	@GetMapping
	public ResponseEntity<Message> findAll() {
		
		List<Notice> noticeList = noticeService.findAll();
		//log.info("공지사항 목록 : {}", noticeList);
		
		if(noticeList.isEmpty()) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Message.builder()
															  .message("조회 결과 없음").build());
		} else {
			
			Message responseMsg = Message.builder().
										data(noticeList).
										message("조회 요청 성공").
										build();
			
			return ResponseEntity .status(HttpStatus.OK).body(responseMsg);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Message> findById(@PathVariable int noticeNo) {
		
		Notice notice = noticeService.findById(noticeNo);
		
		if(notice == null) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Message.builder().message("조회 결과 없음").build());
			
		} else {
			
			Message responseMsg = Message.builder().message("조회 요청 성공").data(notice).build();
			
			return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
					
					
		}
		
	}
	
	@PostMapping
	public ResponseEntity<Message> save(Notice notice) {
		
		// log.info("잉 : {}", notice);
		
		// 값이 입력되지 않았으면 작성자에게 보여줄 메세지
		if("".equals(notice.getNoticeTitle()) || "".equals(notice.getNoticeContent()) || "".equals(notice.getNoticeWriter())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message.builder().message("서비스 요청 실패").data("필수 파라미터 값 누락").build());
		}
		
		int result = noticeService.save(notice);
		
		if(result == 0 ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Message.builder().message("추가 실패").build());
		} else {
		Message responseMsg = Message.builder().data("공지사항 추가 성공").message("서비스 요청 성공").build();
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMsg);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Message> deleteById(@PathVariable int noticeNo) {
		
		int result = noticeService.delete(noticeNo);
		
		if(result == 0) {
			return ResponseEntity.status(HttpStatus.OK).body(Message.builder().message("게시글이 존재하지 않음").build());
		}
		Message response = Message.builder().data("삭제 성공").message("서비스 요청 성공").build();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
	
}


	/*
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
	/*
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
*/