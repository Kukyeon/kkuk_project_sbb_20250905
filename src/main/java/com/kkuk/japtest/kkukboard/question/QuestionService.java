package com.kkuk.japtest.kkukboard.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.kkuk.japtest.kkukboard.DataNotFoundException;
import com.kkuk.japtest.kkukboard.answer.Answer;
import com.kkuk.japtest.kkukboard.user.SiteUser;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import javassist.SerialVersionUID;
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
	
	public void novote(Question question, SiteUser siteUser) { //-> UPDATE 문으로 만들어줘야함
		question.getNovoter().add(siteUser);
		//question->추천을 받은 글의 번호로 조회한 질문 엔티티
		// question의 멤버인 voter를 get 하여 voter에 추천을 누른 유저의 엔티티를 추가해줌
		questionRepository.save(question);
	}
	
	@Transactional
	public void hit(Integer id) { // 조회수 증가
		
		questionRepository.updateHit(id);
		
	}
	// 리스트를 페이징하여 조회
	public Page<Question> getPageQuestion(int page, String kw){
		
		//Specification<Question> spec = search(kw);
		
		int size = 10; // 1페이지당 보여줄 갯수 
		
		int startRow = page * size;
		int endRow = startRow + size;
		
		//검색어 없이 리스트 조회
		List<Question> pageQuestionList = questionRepository.findQuestionsWithPaging(startRow, endRow);
		//long totalQuestion = questionRepository.count(); // 모든 글 갯수가져오기
		
		//검색어로 검색 결과 리스트 조회
		List<Question> searchQuestionList = questionRepository.searchQuestionWithPaging(kw, startRow, endRow);
		//int totalSearchQuestion = searchQuestionList.size();//검색된 총 질문글 수
		int totalSearchQuestion = questionRepository.countSearchResult(kw);
		
		//Page<Question> pagingList = new PageImpl<>(pageQuestionList, PageRequest.of(page, size), totalQuestion);
		Page<Question> pagingList = new PageImpl<>(searchQuestionList, PageRequest.of(page, size), totalSearchQuestion);
		return pagingList;
	}
	
	private Specification<Question> search(String kw) { //키워드(kw) 검색 조회
		
		return new Specification<Question>() {
			private static final long SerialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
			query.distinct(true); // distinct -> 중복제거
			Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); //question + siteUser left 테이블 조인
			Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); //question + answer left 테이블 조인
			Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT); // answer + siteUser left 테이블 조인
			
			return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 질문 제목에서 검색어 조회
					cb.like(q.get("content"), "%" + kw + "%"), // 질문 내용에서 검색어 조회
					cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자에서 검색어 조회
					cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용에서 검색어 조회
					cb.like(u2.get("username"), "%" + kw + "%") // 답변 작성자에서 검색어 조회
					)
				
				;
			}
		};
	}
	
	
	
}