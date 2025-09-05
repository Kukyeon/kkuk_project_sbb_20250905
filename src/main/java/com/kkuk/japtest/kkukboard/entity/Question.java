package com.kkuk.japtest.kkukboard.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity // DB테이블과 매핑할 entity 클래스로 설정
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 질문게시판의 글번호(기본키-자동증가 옵션)
	
	@Column(length = 200) // 게시판의 제목은 200자까지 가능
	private String subject; // 질문게시판의 제목
	
	@Column(columnDefinition = "TEXT")
	private String content; //질문게시판의 내용
	
	
	private LocalDateTime createDate;
	
	//1:N 관계 -> 질문:답변들 -> @OneToMany
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
}
