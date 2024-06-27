package com.kh.ajax.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Menu;

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
	
	@ResponseBody
	@GetMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String selectMenu(int menuNumber) {
		//요청 처리를 잘 했다는 가정 하에 데이터 응답
		
		/*
		 * DB에 존재하는 메뉴 테이블
		 * 
		 * | 메뉴번호  |  메뉴이름  |  가격   |  재료  |
		 * |    1    |  순두부   |  9000  | 순두부  |
		 * 
		 * 
		 * */
		
		//JSON(Java Script Object Notation)
		
		Menu menu = new Menu(1, "순찌", 9000, "순두부");
		
		/*
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append("\"menuNumber\" : " + "'" + menu.getMenuNumber() + "', ");
		sb.append("\"menuName\" : " + "'" + menu.getMenuName() + "', ");
		sb.append("\"price\" : " + "'" + menu.getPrice() + "', ");
		sb.append("\"material\" : " + "'" + menu.getMaterial() + "', ");
		sb.append("}");
		
		return sb.toString();
		*/
		
		/*
		 * {material: '순두부',
		 * 	menuNumber: 1,
		 * 	price: 9000,
		 * 	menuName: '순찌'}
		 * 
		 * VO의 필드명이 키값이 됨
		 * */
		
		JSONObject jobj = new JSONObject();
		
		jobj.put("menuNumber", menu.getMenuNumber());
		jobj.put("menuName", menu.getMenuName());
		jobj.put("price", menu.getPrice());
		jobj.put("material", menu.getMaterial());
		
		//key-value 형태로 값을 저장하며 메소드 put을 호출하여 추가한다 = map과 동일 // JSONObject = Map
		
		return jobj.toJSONString();
	}
	
	@ResponseBody
	@GetMapping(value="ajax3.do", produces="application/json; charset=UTF-8")
	public Menu ajax3Method(int menuNumber) {
		Menu menu = new Menu(1, "순찌", 9000, "순두부");
		
		return menu;
		
		/*
		Gson이 아래 작업을 대신 해줌 -> 하위 버전은 호환x
		
		jobj.put("menuNumber", menu.getMenuNumber());
		jobj.put("menuName", menu.getMenuName());
		jobj.put("price", menu.getPrice());
		jobj.put("material", menu.getMaterial());
		
		 * */
	}
	
	@ResponseBody
	@GetMapping("find.do")
	public String findAll() {
		
		List<Menu> menus = new ArrayList();

		//selectList의 결과가 menus에 담겨있다고 가정.
		//List<Menu> list = MenuService.findAll();
		menus.add(new Menu(1, "순찌", 9000, "순두부"));
		menus.add(new Menu(2, "김찌", 8000, "김치"));
		menus.add(new Menu(3, "된찌", 8000, "된장"));

		//key-vlaue 형태의 객체 3개를 한번에 보내기 위해 배열에 담아 JSON으로 전달
		
		JSONObject jobj1 = new JSONObject();
		
		jobj1.put("menuNumber", menus.get(0).getMenuNumber());
		jobj1.put("menuName", menus.get(0).getMenuName());
		jobj1.put("price", menus.get(0).getPrice());
		jobj1.put("material", menus.get(0).getMaterial());
		
		JSONObject jobj2 = new JSONObject();
		
		jobj2.put("menuNumber", menus.get(0).getMenuNumber());
		jobj2.put("menuName", menus.get(0).getMenuName());
		jobj2.put("price", menus.get(0).getPrice());
		jobj2.put("material", menus.get(0).getMaterial());
		
		JSONObject jobj3 = new JSONObject();
		
		jobj3.put("menuNumber", menus.get(0).getMenuNumber());
		jobj3.put("menuName", menus.get(0).getMenuName());
		jobj3.put("price", menus.get(0).getPrice());
		jobj3.put("material", menus.get(0).getMaterial());
		
		//배열로는 객체의 전송이 불가능함
		//List로는 JSON이 자바스크립트의 형태로 변환시킬 수 없음, 해당 작업을 가능하게 해 주는 것이 JSONArray
		
		JSONArray jArr = new JSONArray();
		
		/*
		jArr.add(jobj1);
		jArr.add(jobj2);
		jArr.add(jobj3);
		*/
		
		/*
		for(int i=0;i<menus.size();i++) {
			JSONObject jobj = new JSONObject();
			jobj.put("menuNumber", menus.get(i).getMenuNumber());
			jobj.put("menuName", menus.get(i).getMenuName());
			jobj.put("price", menus.get(i).getPrice());
			jobj.put("material", menus.get(i).getMaterial());
			
			jArr.add(jobj);
		*/
		//Gson이 위의 작업을 대체
		return new Gson().toJson(menus);
		}
		
		
	}
