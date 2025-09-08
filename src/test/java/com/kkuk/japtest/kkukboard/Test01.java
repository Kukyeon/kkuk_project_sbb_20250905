package com.kkuk.japtest.kkukboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kkuk.japtest.kkukboard.Question.Question;
import com.kkuk.japtest.kkukboard.Question.QuestionRepository;

@SpringBootTest
public class Test01 {

	@Autowired
	private QuestionRepository questionRepository;
	
//	@Test
//	@DisplayName("질문글 저장하기 테슷흐")
//	public void testJpa1() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?"); // 질문 제목 
//		q1.setContent("sbb에 대해 알고 싶습니다."); // 질문내용
//		q1.setCreatedate(LocalDateTime.now()); // 현재시간넣기
//		//q1->entity 생성 완료
//		questionRepository.save(q1);
//		
//		
//		Question q2 = new Question();
//		q2.setSubject("스플링부트 모델이 무엇인가요?"); // 질문 제목 
//		q2.setContent("아이디는 자동생성되는게 맞나요."); // 질문내용
//		q2.setCreatedate(LocalDateTime.now()); // 현재시간넣기
//		//q1->entity 생성 완료
//		questionRepository.save(q2);
//	}
	
	@Test
	@DisplayName("모든 질문글 조회하기 테슷흐")
	public void testJpa2() {
		List<Question> allQuestion = questionRepository.findAll();
		assertEquals(2, allQuestion.size()); // 예상 결과 확인하기(기댓값 : 실제값)
		
		Question question = allQuestion.get(0); // 첫번째 질문글 가져오기
		assertEquals("sbb가 무엇인가요?", question.getSubject());
	}
	
	@Test
	@DisplayName("질문 글 번호(기본키인 id)로 조회하기 테슷흐")
	public void testJpa3() {
	Optional<Question> qOptional = questionRepository.findById(4); // 기본키로조회->1번글 가져오기
	if(qOptional.isPresent()) { // 참이면 기본키 번호가 존재
		Question question = qOptional.get(); // 글꺼내기
		assertEquals("sbb가 무엇인가요?", question.getSubject());
	}
	}
	//질문글 제목으로 테스트
	@Test
	@DisplayName("질문글 제목으로 테스트하기")
	public void testJpa4() {
		Question question = questionRepository.findBySubject("sbb가 무엇인가요?");
		//select * from question where subject='sbb가 무엇인가요?'
		assertEquals(4, question.getId());
		
	}
	
	@Test
	@DisplayName("질문글 제목과 내용으로 테스트하기")
	public void testJpa5() {
		Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해 알고 싶습니다.");
		//select * from question where subject='sbb가 무엇인가요?'and content='sbb에 대해 알고 싶습니다.'
		assertEquals(4, question.getId());
		
	}
	
	@Test
	@DisplayName("제목에 특정 단어가 들어간 레코드를 조회하는 글 테스트하기")
	public void testJpa6() {
		List<Question> questionList = questionRepository.findBySubjectLike("sbb%");
		//select * from question where subject Like %?%
		Question question = questionList.get(0);
		assertEquals("sbb가 무엇인가요?", question.getSubject());
		
	}
}
