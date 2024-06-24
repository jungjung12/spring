package com.kh.spring.notice.model.service;

import java.util.List;
import java.util.Map;

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
	public List<Notice> noticeAll(Map<String, Integer> map) {
		return noticeRepository.noticeAll(sqlSession, map);
	}
	@Override
	public int insert(Notice notice) {
		return noticeRepository.insert(sqlSession, notice);
	}
	@Override
	public int noticeCount() {
		return noticeRepository.noticeCount(sqlSession);
	}
	
	
}
