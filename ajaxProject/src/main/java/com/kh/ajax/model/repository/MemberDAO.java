package com.kh.ajax.model.repository;

public class MemberDAO {

public String isIdFlag(SqlSessionTemplate sqlSession, String id) {

return sqlSession.selectOne("idMapper.isIdFlag");

}