package com.kh.spring.string.run;

import com.kh.spring.string.contoller.StringController;

public class StringRun {
	public static void main(String[] args) {
		
		StringController sc = new StringController();
		
		sc.constructorString();
		
		/*Object 클래스를 상속 받아 다른 클래스에서 사용 가능
		 * 
		System.out.println(sc);
		sc.equals(sc);
		
		*/
	}
}
