package com.kkuk.japtest.kkukboard.answer;

import java.time.LocalDateTime;

import com.kkuk.japtest.kkukboard.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Entity
@Table(name = "answer")
@SequenceGenerator(
		name = "ANSWER_SEQ_GENERATOR", // JPA 내부 시퀀스 이름
		sequenceName = "ANSWER_SEQ", // 실제 DB시퀀스 이름
		initialValue = 1, // 시퀀스 시작 값
		allocationSize = 1 // 시퀀스 증가 값
		)
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_SEQ_GENERATOR")
	private Integer id; // 기본키(자동증가옵션)
	
	@Column(length = 500)
	private String content; // 답변게시판 내용
	
	
	private LocalDateTime creatdate;
	
	//N:1 관계 - > 답변 : 질문 -> @ManyToOne
	@ManyToOne
	private Question question;
	
}
