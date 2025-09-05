package com.kkuk.japtest.kkukboard;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kkuk.japtest.kkukboard.entity.Question;
import com.kkuk.japtest.kkukboard.repository.QuestionRepository;

@SpringBootTest
public class Test01 {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	public void testJpa1() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?"); // 질문 제목 
		q1.setContent("sbb에 대해 알고 싶습니다."); // 질문내용
		q1.setCreateDate(LocalDateTime.now()); // 현재시간넣기
		//q1->entity 생성 완료
		questionRepository.save(q1);
		
		
		Question q2 = new Question();
		q2.setSubject("스플링부트 모델이 무엇인가요?"); // 질문 제목 
		q2.setContent("아이디는 자동생성되는게 맞나요."); // 질문내용
		q2.setCreateDate(LocalDateTime.now()); // 현재시간넣기
		//q1->entity 생성 완료
		questionRepository.save(q2);
	}
}
