package com.kh.spring.notice.model.vo;

import lombok.Builder;

//조회의 성공 여부에 따라 메세지를 송출해줄 칭긔
@Builder
public class Message {
	
	private String message;
	private Object data;
}
