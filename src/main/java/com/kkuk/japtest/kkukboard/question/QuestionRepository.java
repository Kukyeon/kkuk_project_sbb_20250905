package com.kkuk.japtest.kkukboard.question;

import java.util.List;

import javax.swing.Spring;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

	
	public Question findBySubject(String subject);
	//select * from question where subject=?
	
	public Question findBySubjectAndContent(String subject, String content);
	//select * from question where subject=? and content =?
	
	public List<Question> findBySubjectLike(String keyword); // 특정 단어가 포함된 레코드 반환
	//select * from question where subject Like %?%
	
	//페이징 관련
	//public Page<Question> findAll(Pageable pageable);
	@Modifying
	@Query( 
			value = "UPDATE question SET hit=hit+1 WHERE id = :id"
			, nativeQuery = true)
	public void updateHit(@Param("id") Integer id); // 질문글의 기본키 번호로 조회수 증가
}
