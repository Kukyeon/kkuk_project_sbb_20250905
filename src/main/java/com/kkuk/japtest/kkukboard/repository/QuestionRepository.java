package com.kkuk.japtest.kkukboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkuk.japtest.kkukboard.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

}
