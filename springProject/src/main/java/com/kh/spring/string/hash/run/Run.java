package com.kh.spring.string.hash.run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kh.spring.string.hash.model.vo.Student;

public class Run {
	public static void main(String[] args) {
		
		//문자열만 담을 수 있는 HashSet
		
		HashSet<String> set = new HashSet();
		
		ArrayList<String> list = new ArrayList();
		List l = new ArrayList();
		
		l.add("Hello");
		
		System.out.println(((String)l.get(0)).charAt(0));
		
		list.add("Hello");
		System.out.println(list.get(0).charAt(0));
		
		Set<Student> students = new HashSet(); 
	}
}
