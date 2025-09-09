package com.kkuk.japtest.kkukboard;

import java.awt.desktop.QuitResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kkuk.japtest.kkukboard.question.QuestionService;

@SpringBootTest
public class DummyDataInputTest {

	@Autowired
	private QuestionService questionService;
	
	@Test
	public void testDummy() {
		for(int i=1 ; i<=200; i++) { // 200개의 질문더미데이터생성
		String subject = String.format("테스트 데이터입니다.:[%03d]", i);
		String content = "연습 내용 더미 데이터 입니다.";
		questionService.create(subject, content);
		}
	}
	
}
