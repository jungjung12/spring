package com.kh.spring.common.templete;

import com.kh.spring.common.model.vo.PageInfo;

public class PageTemplete {

	public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		
		int maxPage = (int)Math.ceil((double)listCount/ boardLimit);
		int startPage = (currentPage - 1) * pageLimit + 1;
		int endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) endPage = maxPage;
		
		return PageInfo.builder()
				.listCount(listCount)
				.currentPage(currentPage)
				.pageLimit(pageLimit)
				.boardLimit(boardLimit)
				.maxPage(maxPage)
				.startPage(startPage)
				.endPage(endPage)
				.build(); 
	}
}
