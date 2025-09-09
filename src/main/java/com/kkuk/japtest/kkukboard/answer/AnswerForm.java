package com.kkuk.japtest.kkukboard.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {

	@NotEmpty(message = "내용은 필수항목입니다.")
	@Size(min = 5, message = "5글자 이상 입력해주세요")
	private String content;
	
	
}
