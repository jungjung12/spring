package com.kh.spring.notice.model.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.notice.model.rerository.NoticeRepository;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

	private final NoticeRepository noticeRepository;
	private final SqlSessionTemplate sqlSession;
	@Override
	public List<Notice> noticeAll() {
		return noticeRepository.noticeAll(sqlSession);
	}
	@Override
	public int insert(Notice notice) {
		return noticeRepository.insert(sqlSession, notice);
	}
	
	
}
