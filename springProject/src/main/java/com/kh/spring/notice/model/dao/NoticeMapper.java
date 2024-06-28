package com.kh.spring.notice.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.notice.model.vo.Notice;

// @Mapper --> 마이바티스 버전 3부터 지원 --> mapper.xml이 하던 역할을 수행, 
@Mapper
public interface NoticeMapper {

	List<Notice> findAll();
	
	Notice findById(int NoticeNo);
	
	int save(Notice notice);
	
	int update(Notice nitice);
	
	int delete(int NoticeNo);
}
