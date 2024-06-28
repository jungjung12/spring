package com.kh.spring.notice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Notice {

	private int noticeNo;	//글 번호
	private String noticeTitle;	//글 제목
	private String noticeWriter;	//작성자
	private String noticeContent;	//내용
	private Date createDate;	//작성일
}
