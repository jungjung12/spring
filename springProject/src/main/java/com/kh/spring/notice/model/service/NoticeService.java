package com.kh.spring.notice.model.service;

import java.util.List;

import com.kh.spring.notice.model.vo.Notice;

public interface NoticeService {

	List<Notice> noticeAll();

	int insert(Notice notice);

	int noticeCount();

}
