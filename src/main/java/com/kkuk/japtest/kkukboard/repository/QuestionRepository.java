package com.kkuk.japtest.kkukboard.repository;

import java.util.List;

import javax.swing.Spring;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkuk.japtest.kkukboard.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

	
	public Question findBySubject(String subject);
	//select * from question where subject=?
	
	public Question findBySubjectAndContent(String subject, String content);
	//select * from question where subject=? and content =?
	
	public List<Question> findBySubjectLike(String keyword); // 특정 단어가 포함된 레코드 반환
	//select * from question where subject Like %?%
	
	
}
