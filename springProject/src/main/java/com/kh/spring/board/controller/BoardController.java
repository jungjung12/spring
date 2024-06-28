package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.templete.PageTemplete;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("boardList")
	public String forwording(@RequestParam(value="page", defaultValue="1") int page, Model model) {
		
		//페이징 처리
		
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
		
		//if(endPage>maxPage) endPage = maxPage;
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
		
		Map<String, Integer> map = new HashMap();
		
		int startValue = (currentPage - 1) * pageLimit + 1;
		int endValue = startPage + pageLimit - 1;
		
		map.put("startValue", startValue);
		map.put("endValue", endValue);
		
		List<Board> boardList = boardService.findAll(map);
		
		// log.info("조회 된 게시글 개수 : {}", boardList.size());
		// log.info("조회 된 게시글 목록 : {}", boardList);
		
		model.addAttribute("list", boardList);
		model.addAttribute("pageInfo", pageInfo);
		
		return "board/list";
	}
	
	@GetMapping("search.do")
	public String search(String condition, String keyword, @RequestParam(value="page", defaultValue="1") int page, Model model) {
		
		//log.info("입력한 키워드 : {}", keyword);	//예측 불가능한 경우의 수
		//log.info("검색 조건 : {}" ,condition);		//예측 가능한 경우의 수
		
		//사용자가 선택한 조건과 입력한 키워드를 가지고 페이징 처리를 끝낸 후 결과를 들고가야 함
		
		Map<String, String> map = new HashMap();
		map.put("condition", condition);
		map.put("keyword", keyword);
		
		int searchCount = boardService.searchCount(map);
		//log.info("개수 : {}", searchCount);
		int currentPage = page;
		int pageLimit = 3;
		int boardLimit = 3;
		
		/*
		 * int maxPage = (int)Math.ceil((double)listCount/ boardLimit); int startValue =
		 * (currentPage - 1) * pageLimit + 1; int endValue = startPage + pageLimit - 1;
		 * if(endPage > maxPage) endPage = maxPage;
		 * 
		 * PageInfo pageInfo = PageInfo.builder() .listCount(listCount)
		 * .currentPage(currentPage) .pageLimit(pageLimit) .boardLimit(boardLimit)
		 * .maxPage(maxPage) .startPage(startPage) .endPage(endPage) .build();
		 */
		
		PageInfo pageInfo = PageTemplete.getPageInfo(searchCount, currentPage, pageLimit, boardLimit);
		
		//Mybatis에서 제공하는 RowBounds
		RowBounds rowBouds = new RowBounds((currentPage -1) * boardLimit,boardLimit);
		
		/*offset, limit 사용
		 * (currentPage - 1) * boardLimit()
		 * */
		
		List<Board> boardList = boardService.findByConditionAndKeyword(map, rowBouds);
		
		model.addAttribute("list", boardList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("condition", condition);
		
		//log.info("컨디션 : {}", condition);
		
		return "board/list";
		
	}
	
	@GetMapping("boardForm.do")
	public String boardForwording() {
		
		return "board/insertForm";
		
	}
	
	@PostMapping("insert.do")
	public String insert(Board board, HttpSession session, MultipartFile upfile, Model model) {
		
		/*
		 * log.info("게시글 정보 : {}", board);
		 * log.info("파일 정보 : {}", upfile);
		 */
		
		//첨부파일 존재 여부 확인
		//Multipart는 무조건 생성 -> fileName 필드에 원본명이 들어있는지 여부 확인
		//전달 된 파일이 존재할 경우 => 파일 업로드
		
		if(!upfile.getOriginalFilename().equals("")) {
			//파일이 존재하면 업로드
			//서버에 업로드될 파일명을 바꿔서 업로드
			/*
			 * String originName = upfile.getOriginalFilename();
			 * 
			 * //확장자 앞의 "." 얻기 String ext =
			 * originName.substring(originName.lastIndexOf("."));
			 * 
			 * //int num = (int)(Math.random() * 값의 범위) + 시작값; int num = (int)(Math.random()
			 * * 100) + 1;
			 * 
			 * //new SimpleDateFormat() String currentTime = new
			 * SimpleDateFormat("yyyyMMDDHHmmss").format(new Date());
			 * 
			 * String savePath =
			 * session.getServletContext().getRealPath("/resources/uploardFiles/");
			 * 
			 * String changeName = "KH_" + currentTime + "_" + num + ext;
			 * 
			 * try { upfile.transferTo(new File(savePath + changeName)); } catch
			 * (IllegalStateException e) { e.printStackTrace(); } catch (IOException e) {
			 * e.printStackTrace(); }
			 * board.setOriginName(originName);
			board.setChangeName("resources/uploardFiles/" + changeName);
			 */
			
			//첨부파일이 존재할 경우 -> Board 객체에 originName, changeName을 담아준다.
			board.setOriginName(upfile.getOriginalFilename());
			board.setChangeName(saveFile(upfile, session));
			
		}
			//boardService.insert(board);
			
			if(boardService.insert(board) > 0) {
				
				session.setAttribute("alertMsg", "글 작성 성공");
				
				//반드시 리다이렉트 해야 함
				return "redirect:boardList";
				
			} else {
				
				model.addAttribute("errorMsg", "작성 실패");
				
				return "common/errorPage";
			}
		
		
	}
	
	@GetMapping("board-detail")
	public ModelAndView findByBoardNo(int boardNo, ModelAndView mv) {
		
		//boardService.increaseCount(boardNo);
		
		if(boardService.increaseCount(boardNo) > 0) {
			
			mv.addObject("board", boardService.findById(boardNo)).setViewName("board/boardDetail");
			
		} else {
			
			mv.addObject("errorMsg", "게시글 상세 조회 실패").setViewName("common/errorPage");
			
		}
		return mv;
	}
	
	/*
	 * deleteById : Client에세 정수형의 boardNo를 전달받아 Board 테이블에 있는 정보를 update
	 * 
	 * @param filePath : 요청 처리 시 첨부파일을 제거하기 위해 파일이 저되어 있는 경로 및 파일명
	 * @param boardNo : 각 행을 식별하기 위한 PK
	 * 
	 * @return : 반환된 view의 논리적인 경로
	 * 
	 * */
	
	@PostMapping("boardDelete.do")
	public String deleteById(int boardNo, String filePath, HttpSession session, Model model) {
		
		if(boardService.deleteById(boardNo) > 0) {
			
			//if(!filePath.equals("")) > 문자열 비교 기준을 filePath로 잡으면 input 요소의 name 값과 상이할 때 빈 문자열이 아닌, null 값이 들어가 버리기 때문에 NullPointExetion 발생률이 증가
			if(!"".equals(filePath)) {
				
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			
			session.setAttribute("alertMsg", "게시글 삭제 성공");
			return "redirect:boardList";

			
		} else {
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			
			return "common/errorPage";
			
		}
		
	}
	
	@PostMapping("boardUpdateForm.do")
	public ModelAndView updateForm(ModelAndView mv, int boardNo) {
		
		mv.addObject("board", boardService.findById(boardNo)).setViewName("board/boardUpdate");
		
		return mv;
	}
	
	@PostMapping("board-update.do")
	public String update(Board board, MultipartFile reUpFile, HttpSession session) {
		
		/*
		 * 파일 정보 update
		 * 
		 * 1. 기존 파일 X, 새로운 파일 X -> 그대로 update
		 * 2. 기존 파일 O, 새로운 파일 X -> origin : 기존 파일 이름, change : 기존 파일 경로
		 * 2. 기존 파일 X, 새로운 파일 O -> origin : 새로운 파일 이름, change : 새로운 파일 경로
		 * 2. 기존 파일 O, 새로운 파일 O -> origin : 새로운 파일 이름, change : 새로운 파일 경로
		 * 
		 * */
		
		// 새로운 첨부파일이 존재하는지 여부 확인
		
		if(!reUpFile.getOriginalFilename().equals("")) {
			
			board.setOriginName(reUpFile.getOriginalFilename());
			board.setChangeName(saveFile(reUpFile, session));
			
		}
		
		if(boardService.update(board) > 0) {
			
			session.setAttribute("alertMsg", "수정 성공");
			return "redirect:board-detail?boardNo="+board.getBoardNo();
		} else {
			
			session.setAttribute("errorMsg", "수정 실패");
			return "common/errorPage";
		}
		
	}
	
	// 중복코드 방지용 -> 밖에 메소드로 빼두고 핑료한 곳에서 사용
	
	public String saveFile(MultipartFile upfile, HttpSession session) {
		
		String originName = upfile.getOriginalFilename();
		
		//확장자 앞의 "." 얻기
		String ext = originName.substring(originName.lastIndexOf("."));
		
		//int num = (int)(Math.random() * 값의 범위) + 시작값;
		int num = (int)(Math.random() * 100) + 1;
		
		//new SimpleDateFormat()
		String currentTime = new SimpleDateFormat("yyyyMMDDHHmmss").format(new Date());
		
		String savePath = session.getServletContext().getRealPath("/resources/uploardFiles/");
		
		String changeName = "KH_" + currentTime + "_" + num + ext;
		
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "resources/uploardFiles/" + changeName;
	}
	
	@GetMapping("image-board")
	public String images(Model model) {
		
		// List<Board> images = boardService.selectImages();
		
		model.addAttribute("board", boardService.selectImages());
				
		return "board/imageList";
	}
	
	@ResponseBody
	@GetMapping(value="reply", produces = "application/json; charset=UTF-8")
	public String selectReply(int boardNo) {
 		
		return new Gson().toJson(boardService.selectReply(boardNo));
	}
	
	@ResponseBody
	@PostMapping("reply")
	public String saveReply(Reply reply) {
		
		return boardService.insertReply(reply) > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@GetMapping("board-reply")
	public Board boardAndReply(int boardNo) {
		
		return boardService.boardAndReply(boardNo);
	}
	
}
