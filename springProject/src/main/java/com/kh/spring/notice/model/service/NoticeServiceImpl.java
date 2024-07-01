package com.kh.spring.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.spring.notice.model.dao.NoticeMapper;
import com.kh.spring.notice.model.vo.Notice;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeMapper noticeMapper;
	
	@Override
	public List<Notice> findAll() {
		return noticeMapper.findAll();
	}

	@Override
	public Notice findById(int noticeNo) {
		return noticeMapper.findById(noticeNo);
	}

	@Override
	public int save(Notice notice) {
		return noticeMapper.save(notice);
	}

	@Override
	public int update(Notice nitice) {
		return 0;
	}

	@Override
	public int delete(int NoticeNo) {
		return noticeMapper.delete(NoticeNo);
	}

	/*
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
	*/
	
	
}
