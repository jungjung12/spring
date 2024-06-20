package com.kh.spring.member.model.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {

	int returnNum();
	
	Member login(Member member);
	
	int insert(Member member);
	
	int update(Member member);
	
	int delete(String userId);
}
