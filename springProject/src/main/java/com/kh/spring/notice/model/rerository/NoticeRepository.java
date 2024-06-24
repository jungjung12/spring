package com.kh.spring.notice.model.rerository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.notice.model.vo.Notice;

@Repository
public class NoticeRepository {

	public List<Notice> noticeAll(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("noticeMapper.noticeAll");
	}

	public int insert(SqlSessionTemplate sqlSession, Notice notice) {
		return sqlSession.insert("noticeMapper.insert", notice);
	}

}
