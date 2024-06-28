package com.kh.spring.notice.model.service;

import java.util.List;

import com.kh.spring.notice.model.vo.Notice;

public interface NoticeService {

	/*
	int noticeCount();
	
	List<Notice> noticeAll(Map<String, Integer> map);

	int insert(Notice notice);
	*/

	List<Notice> findAll();
	
	Notice findById(int NoticeNo);
	
	int save(Notice notice);
	
	int update(Notice nitice);
	
	int delete(int NoticeNo);
}
