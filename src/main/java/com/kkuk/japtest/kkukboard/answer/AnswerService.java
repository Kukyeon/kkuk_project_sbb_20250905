package com.kkuk.japtest.kkukboard.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkuk.japtest.kkukboard.DataNotFoundException;
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
	
	public Answer getAnswer(Integer id) {
		Optional<Answer> _answer = answerRepository.findById(id); // 기본키로 entity 조회
		
		if(_answer.isPresent()) {
			return _answer.get(); // 해당 answer 엔티티가 올바르게 반환
		}else {
			throw new DataNotFoundException("해당 답변이 존재하지않습니다.");
		}
	}
	
	public void modify(Answer answer, String content) { //답변 수정하기
		answer.setContent(content);
		answer.setModifydate(LocalDateTime.now()); //답변 수정일시
		answerRepository.save(answer);
	}
	
	public void delete(Answer answer) {
		answerRepository.delete(answer);
	}
}
