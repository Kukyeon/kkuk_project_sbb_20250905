package com.kkuk.japtest.kkukboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kkuk.japtest.kkukboard.Answer.Answer;
import com.kkuk.japtest.kkukboard.Answer.AnswerRepository;
import com.kkuk.japtest.kkukboard.Question.Question;
import com.kkuk.japtest.kkukboard.Question.QuestionRepository;

@SpringBootTest
public class Test02 {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerretository;
	
//	@Test
//	@DisplayName("질문 게시판 제목 수정하기")
//	public void testJpa1() {
//		Optional<Question> optionalquestion = questionRepository.findById(4); // 기본키 글번호가 4번인 레코드
//		assertTrue(optionalquestion.isPresent()); // 기본키로 가져온 레코드가 존재하면 true -> 테스트 통과
//		// 기본키로 가져온 레코드가 존재하지않으면 false->테스트 종료
//		Question question = optionalquestion.get();
//		question.setSubject("수정된 제목");
//		
//		this.questionRepository.save(question);
// 	}
	
//	@Test
//	@DisplayName("질문 게시판 글 삭제하기 테스트")
//	public void testJpa2() {
//		assertEquals(2, questionRepository.count());
//		//questionRepository.count() -> 모든 행(레코드)의 갯수를 반환
//		Optional<Question> Qoptional = questionRepository.findById(4); // 4번글 가져오기
//		assertTrue(Qoptional.isPresent()); //4번글의 존재여부 확인
//		Question question = Qoptional.get();
//		questionRepository.delete(question);// delete(entity)->해당글이 삭제
//		assertEquals(1, questionRepository.count());
// 	}
	
//	@Test
//	@DisplayName("답변 게시판 글 생성하기 테스트")
//	public void testJpa3() {
//		//답변 -> 질문글의 번호를 준비
//		
//		Optional<Question> Qoptional = questionRepository.findById(5); // 5번글 가져오기
//		assertTrue(Qoptional.isPresent()); //5번글의 존재여부 확인
//		Question question = Qoptional.get();
//		
//		Answer answer = new Answer();
//		answer.setContent("네 자동으로 생성됩니다."); // 답변 내용 넣어주기
//		answer.setCreatdate(LocalDateTime.now()); // 현재시간 넣어주기
//		answer.setQuestion(question); // 답변이 달릴 질문글을 필드로 넣어주기
//		answerretository.save(answer);
//	}
	
//	@Test
//	@DisplayName("답변 게시판 글 조회하기 테스트")
//	public void testJpa4() {
//		Optional<Answer> aOptional = answerretository.findById(1); // 답변글 테이블에서 1번글 가져오기
//		assertTrue(aOptional.isPresent());
//		
//		Answer answer = aOptional.get();
//		assertEquals(5, answer.getQuestion().getId()); //부모질문글의 번호로 확인 테스트
//	}
	
	@Test
	@DisplayName("질문 글을 통해 답변 글들 테스트")
	@Transactional
	public void testJpa5() {
		// 질문글 가져오기
		Optional<Question> qOptional = questionRepository.findById(5); // 5번글 가져오기
		assertTrue(qOptional.isPresent()); //5번글의 존재여부 확인
		Question question = qOptional.get();
		// 질문글 가져오기
		
		List<Answer> answerList = question.getAnswerList(); // 해당 질문글에 달린 답변들의 리스트
		//게으른 초기화문제로 오류 -> question entity가 닫힌 후 초기화를 시도
		// 테스트 과정에서만 발생-> @Transactional 로 에러를 막을수있음
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

