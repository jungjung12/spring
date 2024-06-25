package com.kh.ajax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	
	// 1. HttpServletReponse 객체로 데이터 응답하기(Stream을 이용)
	
	/*
	@GetMapping("ajax1.do")
	public void calSum(String menu, int amount, HttpServletResponse response) throws IOException {
		
		System.out.println("입력한 메뉴 : " + menu);
		System.out.println("입력한 수량 : " + amount);
		
		int price = 0;
		switch(menu) {
		case "샌드위치" : price = 4500; break;
		case "돈까스" : price = 10000; break;
		case "김치찜" : price = 12000; break;
		case "알밥" : price = 7000; break;
		}
		
		price *= amount;
		
		// System.out.println(price);
		response.setContentType("text/html; charset=UTF-8");
		
		response.getWriter().print(price);
	} 
	*/
	
	/*
	 * 2. 응답할 데이터를 문자열로 반환 -> HttpServletReponse를 사용하지 않는 방법
	 * ->String 반환 시 포워딩 -> 응답뷰의 경로로 인식을 해서 리졸버로 전달
	 * 
	 * 때문에 스프링을 이용해 응답 데이터 반환 시에는
	 * =? MessageConverter 로 이동하게끔 지정 ==> @ResponseBody 에노테이션 달아주기
	 * */
	// 한글 외 다른 언어 전송 시 인코딩이 깨지는 문제가 발생. -> 이 안에서 response 객체를 생성하는 것은 불가능하므로 charset을 통해 인코딩 방식을 지정해주어야 함
	@GetMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	@ResponseBody
	public String calSum(String menu, int amount) {
		
		int price = 0;
		switch(menu) {
		case "샌드위치" : price = 4500; break;
		case "돈까스" : price = 10000; break;
		case "김치찜" : price = 12000; break;
		case "알밥" : price = 7000; break;
		}
		
		price *= amount;
		
		return String.valueOf(price);
	}
}
