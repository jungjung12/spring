package com.kh.spring.notice.model.rerository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.spring.notice.model.vo.Notice;

@Repository
public class NoticeRepository {

	public List<Notice> noticeAll(SqlSessionTemplate sqlSession, Map<String, Integer> map) {
		return sqlSession.selectList("noticeMapper.noticeAll", map);
	}

	public int insert(SqlSessionTemplate sqlSession, Notice notice) {
		return sqlSession.insert("noticeMapper.insert", notice);
	}

	public int noticeCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("noticeMapper.noticeCount");
	}

}
