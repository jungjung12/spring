package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.repository.MemberRepository;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final SqlSessionTemplate sqlSession;
	private final MemberRepository memberrepository;
	
	//메소드 사용하는 법 기재
	
	@Override
	public int returnNum() {
		return 1;
	}

	@Override
	public Member login(Member member) {
		
		return memberrepository.login(sqlSession, member);
	}

	@Override
	public int insert(Member member) {
		
		return memberrepository.insert(sqlSession, member);
	}

	@Override
	public int update(Member member) {
		
		return memberrepository.upadte(sqlSession, member);
	}

	@Override
	public int delete(Member member) {
		return 0;
	}

	
}
