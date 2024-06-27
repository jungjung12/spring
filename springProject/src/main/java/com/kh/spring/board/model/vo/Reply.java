package com.kh.spring.board.model.vo;

import lombok.Setter;
import lombok.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Reply {

	private int replyNo;	//댓글 번호
	private String replyContent;	//댓글 내용
	private String replyWriter;		//댓글 작성자
	private int refBoardNo;		//참고해올 게시글 번호
	private String createDate;		//댓글 작성일
	private String status;	//댓글 상태 -> 추후 댓글 관리 기능 추가 대비
}
