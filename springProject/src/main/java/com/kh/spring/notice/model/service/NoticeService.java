package com.kh.spring.notice.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.notice.model.vo.Notice;

public interface NoticeService {

	int noticeCount();
	
	List<Notice> noticeAll(Map<String, Integer> map);

	int insert(Notice notice);
	

}
