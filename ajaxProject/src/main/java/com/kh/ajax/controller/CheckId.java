package com.kh.ajax.controller;

import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/idlCheck")

@RequiredArgsConstructor

@Controller

public class CheckId{

private final IdService idService;

@GetMapping

public void checkid(String id, HttpServlerResponse response) {

String result= idService.checkId(id);

response.setContentType("text/html;charset=UTF-8");

PrintWriter resp = response.getWriter();

if(result != null) {

resp.print("중복된 아이디가 존재합니다.");

}else {

resp.print("사용가능한 아이디입니다.");

}

}catch(Exception e) {

e.printStackTrace();

resp.print("조회하는 도중 오류가 발생했습니다.");

}

}

}

}