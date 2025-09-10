package com.kkuk.japtest.kkukboard.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkuk.japtest.kkukboard.DataNotFoundException;
import com.kkuk.japtest.kkukboard.user.SiteUser;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
//	@Autowired
	private final QuestionRepository questionRepository; 
	// @RequiredArgsConstructor에 의해 생성자 방식으로 주입된 questionRepostitory(final 필드만 가능)
	
	public List<Question> getList() { //모든 질문글 가져오기
		return questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {// 기본키인 질문글 번호로 질문1개 가져오기
		Optional<Question> qOptional = questionRepository.findById(id);
		
		if(qOptional.isPresent()) {
			return qOptional.get(); //question 반환
		} else {
			throw new DataNotFoundException("question not found");
		}
		
	}
	public void create(String subject, String content, SiteUser user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		question.setAuthor(user);//글쓴이 추가
		questionRepository.save(question);
	}
	
	public void modify(Question question ,String subject, String content) { // 질문 글 수정하기
		question.setSubject(subject);//새로운 제목으로 저장
		question.setContent(content);//새로운 내용으로 저장
		question.setModifydate(LocalDateTime.now()); // 수정날자 저장
		questionRepository.save(question); // 질문 글 수정
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	public void vote(Question question, SiteUser siteUser) { //-> UPDATE 문으로 만들어줘야함
		question.getVoter().add(siteUser);
		//question->추천을 받은 글의 번호로 조회한 질문 엔티티
		// question의 멤버인 voter를 get 하여 voter에 추천을 누른 유저의 엔티티를 추가해줌
		questionRepository.save(question);
	}
	@Transactional
	public void hit(Integer id) { // 조회수 증가
		
		questionRepository.updateHit(id);
		
	}
}