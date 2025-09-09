package com.kkuk.japtest.kkukboard.question;

import java.time.LocalDateTime;
import java.util.List;

import com.kkuk.japtest.kkukboard.answer.Answer;
import com.kkuk.japtest.kkukboard.user.SiteUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question")
@Entity // DB테이블과 매핑할 entity 클래스로 설정
@SequenceGenerator(
		name = "QUESTION_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName = "QUESTION_SEQ", // 실제 DB시퀀스 이름
		initialValue = 1, // 시퀀스 시작 값
		allocationSize = 1 // 시퀀스 증가 값
		)
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUESTION_SEQ_GENERATOR")
	private Integer id; // 질문게시판의 글번호(기본키-자동증가 옵션)
	
	@Column(length = 200) // 게시판의 제목은 200자까지 가능
	private String subject; // 질문게시판의 제목
	
	@Column(length = 500)
	private String content; //질문게시판의 내용
	
	
	
	private LocalDateTime createdate;
	
	//1:N 관계 -> 질문:답변들 -> @OneToMany
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	// N:1 관계 -> 질문:작성자 -> @ManyToOne
	@ManyToOne
	private SiteUser author;
}
