package com.kh.spring.string.hash.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

	private String name;
	private int age;
	private int score;
	
	//hashcode()
	@Override
	public int hashCode() {
		
		return 0;
	}
	
	
	
}
