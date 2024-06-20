package com.kh.spring.board.model.service;

import java.util.List;

import com.kh.spring.board.model.vo.Board;

public interface BoardService {

	//게시글 전체 조회 + 페이징 처리
	int boardCount();
	
	//게시들 목록 조회
	
	List<Board> findAll();
	
	//검색 목록 조회
	
	List<Board> serchAll();
	
	//게시글 작성
	
	int insert(Board board);
	
	//게시글 상세보기 > 게시글을 클릭하는 순간 게시글이 삭제되어 실패할 수 있음
	//조회수 증가 > 먼저 선행되어야 함
	int increaseCount(int boardNo);
	
	//게시글 상세 조회
	
	Board findById(int boardNo);
	
	//게시글 수정
	
	int update(Board board);
	
	//게시글 삭제
	
	int delete(int boardNo);
	
	/* 댓글 기능 */
	
	//AJAX, Mybatis를 이용한 댓글 조회
	
	//댓글 작성
}