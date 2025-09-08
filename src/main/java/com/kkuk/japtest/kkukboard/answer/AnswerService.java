package com.kkuk.japtest.kkukboard.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkuk.japtest.kkukboard.question.Question;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public void create(Question question ,String content ) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreatedate(LocalDateTime.now()); // 답변 등록시간
		answer.setQuestion(question);
		answerRepository.save(answer);
	}
	
}
