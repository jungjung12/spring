package com.kh.spring.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class PageInfo {

	private int listCount;	// 게시판의 총 게시글 수
	private int currentPage;	// 현재 페이지(사용자가 요청한 페이지) -> 앞에서 넘길 것
	private int pageLimit;	// 페이지 하단에 보여질 페이징 바의 최대 갯수 -> 10개로 고정
	private int boardLimit;	// 현 페이지에 보여질 게시글의 최대 개수 -> 10개로 고정
	
	private int maxPage;	// 가장 마지막 페이지가 몇 번 페이지인지(페이지의 총 개수)
	private int startPage;	// 페이지 하단에 보여질 페이징 바의 시작 수
	private int endPage;	// 페이지 하단에 보여질 페이징 바의 마지막 수
	
}