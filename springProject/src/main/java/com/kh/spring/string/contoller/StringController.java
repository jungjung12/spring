package com.kh.spring.string.contoller;

public class StringController {

	//String 클래스 : 불변
	
	
	//charArray
	
	/*
	String 클래스의 객체 생성 방법
	
	1. 대입 연산자를 통해서 String 이터럴를 대입하는 방법
	2. 생성자를 호출해서 String 객체로 만들어주는 방법
	*/
	
	//생성자를 호출해서 String 객체를 생성
	public void constructorString() {
		String str1 = new String("Hello");
		String str2 = new String("Hello");
		
		System.out.println(str1);
		System.out.println(str2);
		
		/*
		 * String 클래스의 toString
		 * ; 실제 담겨있는 문자열을 그대로 반환하도록 오버라이딩
		 * 
		 * equels()
		 */
		System.out.println(str1.equals(str2));
		//주소값이 아닌 문자열 리터럴 값을 비교
		
		//hashcode()
		//16진수 > int 형의 10진수
		System.out.println(str1.hashCode());
		System.out.println(str2.hashCode());
		System.out.println("hello".hashCode());
		
		// 주소값을 해시하는 것이 아니라 실제 담긴 '문자열'을 기반으로 해시코드 값을 반환
		
		//식별할 수 있는 값 > 실제로 '같은 객체'인지 판별 가능.
		System.out.println(System.identityHashCode(str1));
		System.out.println(System.identityHashCode(str2));
		//=값만 같은 뿐 다른 객체라는 뜻
	}
		
		//리터럴 대입
		public void assingToString() {
		String str1 = "Hello";
		String str2 = "Hello";
		
		// toString()
		System.out.println(str1);
		System.out.println(str2);
		
		//equels()
		System.out.println(str1.equals(str2));
		
		//hashcode()
		System.out.println(str1.hashCode());
		System.out.println(str2.hashCode());
		
		//identityHashCode()
		System.out.println(System.identityHashCode(str1));
		System.out.println(System.identityHashCode(str2));
	}
		
		
}