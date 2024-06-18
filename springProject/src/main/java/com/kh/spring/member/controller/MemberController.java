package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	private final BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//JSP / servlet
	/*
	 * @RequestMapping("login.do")
	 * public String login(HttpServletRequest request) {
	 * 
	 * String userId = request.getParameter("id"); String userPw =
	 * request.getParameter("pw");
	 * 
	 * log.info("입력한 아이디 : {}", userId); log.info("입력한 비밀번호 : {}", userPw);
	 * 
	 * return "main"; }
	 */
	
	
	/*
	 * @RequestMapping("login.do") 
	 * public String login(@RequestParam("id") String userId, @RequestParam("pw") String userPw) {
	 * 
	 * log.info("입력한 아이디 : {}", userId); log.info("입력한 비밀번호 : {}", userPw);
	 * 
	 * return "main"; }
	 */
	

	//어노테이션 생략
	/*
	 * @RequestMapping("login.do") 
	 * public String login(String id, String pw) {
	 * 
	 * log.info("입력한 아이디 : {}", id); log.info("입력한 비밀번호 : {}", pw);
	 * 
	 * Member member = new Member();
	 * member.setUserId(id); 
	 * member.setUserPw(pw);
	 * 
	 * return "main"; }
	 */
	
	
	//커멘드 객체(ModelAttribute) ; 겍체명과 jsp의 name 속성값 일치

	//되는지 확인
	/*
	 * @RequestMapping("login.do")
	 * public String login(Member member) {
	 * 
	 * log.info("가공된 객체 : {}", member);
	 * 
	 * return "main"; }
	 */
	
	
	/*
	@PostMapping("login.do")
	public String login(Member member, Model model, HttpSession session) {
		
		Member loginUser = memberService.login(member);
		
		if(loginUser == null) {
			
			model.addAttribute("errorMsg", "로그인 실패");
			
			return "common/errorPage";
			
		} else {
			
			session.setAttribute("loginUser", loginUser);
			
			return "redirect:/";
		}
		
	}
	*/
	
	
	
	@PostMapping("login.do")
	public ModelAndView login(Member member, ModelAndView mv, HttpSession session) {
		
		Member loginUser = memberService.login(member);
		
		if(loginUser != null && bcryptPasswordEncoder.matches(member.getUserPwd(), loginUser.getUserPwd())) {
			session.setAttribute("loginUser", loginUser);
			 mv.setViewName("redirect:/");
	         
	      } else { //로그인 성공 => loginUser를 sessinoScope에 담아 응답화면으로
	         mv.addObject("errorMsg", "로그인 실패").setViewName("common/errorPage");
	      }
	      return mv;
		}


	@GetMapping("logout.do")
	public String logout(HttpSession session) {
		
		session.removeAttribute("loginUser");
		
		return "redirect:/";
	}
	
	@GetMapping("enroll.do")
	public String enroll() {
		
		return "member/enrollform";
	}
	
	@PostMapping("join.do")
	public String join(Member member, Model model) {
		
		/* log.info("회원 정보 : {}", member); */
		//log.info("평문 : {}", member.getUserPwd());
		
		String encPwd = bcryptPasswordEncoder.encode(member.getUserPwd());
		
		//log.info("암호화 : {}", encPwd);
		
		member.setUserPwd(encPwd);	//insert 할 member 객체에 pw 값을 암호화 해서 담기

		String viewName = "";
		
		if(memberService.insert(member) > 0) {	//정수 값 - insert가 몇 개나 수행되었는지 1 or 0
		
			viewName = "redirect:/";
			
		} else {
		
			model.addAttribute("errorMsg", "회원가입 실패");
		
			viewName = "common/errorPage";
		
		}
		
		return viewName;
	}
	
	@GetMapping("mypage.do")
	public String mypage(Member member) {
		
		return "member/myPage";
	}
	
	@PostMapping("update.do")
	public String update(Member member, Model model) {
		
		//log.info("정보 수정 : {}", member);
		
		if(memberService.update(member) > 0) {
			
			memberService.login(member);
			
			return "redirect:mypage.do";
			
		} else {
			
			model.addAttribute("errorMsg", "업데이트 실패");
			
			return "common/errorPage";
			
		}
		
		return null;
	}
	
}
	
	/*
	 * @GetMapping("/member/{id}") public void restTest(@PathVariable String id) {
	 * 
	 * log.info("앞단에서 넘긴 값 : {}", id);
	 * 
	 * }
	 */
