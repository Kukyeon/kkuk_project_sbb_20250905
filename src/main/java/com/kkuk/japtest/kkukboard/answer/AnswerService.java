package com.kkuk.japtest.kkukboard.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkuk.japtest.kkukboard.question.Question;
import com.kkuk.japtest.kkukboard.user.SiteUser;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	public void create(Question question ,String content, SiteUser author) {
		Answer answer = new Answer();
		answer.setContent(content);
		answer.setCreatedate(LocalDateTime.now()); // 답변 등록시간
		answer.setQuestion(question);
		answer.setAuthor(author);
		answerRepository.save(answer);
	}
	
}
