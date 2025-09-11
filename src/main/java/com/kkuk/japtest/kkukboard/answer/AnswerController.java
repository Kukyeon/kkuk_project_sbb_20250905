package com.kkuk.japtest.kkukboard.answer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.kkuk.japtest.kkukboard.question.Question;
import com.kkuk.japtest.kkukboard.question.QuestionService;
import com.kkuk.japtest.kkukboard.user.SiteUser;
import com.kkuk.japtest.kkukboard.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/answer")
@Controller
public class AnswerController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/create/{id}") //답변 등록 요청->오는 파라미터 값 : 부모 질문글의 번호
	public String createAnswer(Model model, @PathVariable("id") Integer id, Principal principal ,@Valid AnswerForm answerForm, BindingResult bindingResult) {
		Question question = questionService.getQuestion(id);		
		//principal.getName();//로그인한 유저의 아이디 얻기
		SiteUser siteUser = userService.getUser(principal.getName());
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		Answer answer = answerService.create(question, answerForm.getContent(),siteUser); //DB에 답변 등록
		
		return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/modify/{id}") // 답변 수정하기 위해 띄워주는 폼
	public String answerModify(AnswerForm answerForm ,@PathVariable("id")Integer id, Principal principal) {
		Answer answer = answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		answerForm.setContent(answer.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/modify/{id}") // 답변 수정하기 위한 요청
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult ,@PathVariable("id")Integer id, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = answerService.getAnswer(id);
		if(!answer.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		
		answerService.modify(answer, answerForm.getContent()); // 수정완료
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		// redirect 는 부모글(답변이 달린 질문글)의 번호로 이동
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id")Integer id) {
		Answer answer = answerService.getAnswer(id);
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		answerService.delete(answer);
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId()) ;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/vote/{id}")
	public String answerVote(@PathVariable("id")Integer id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		
		SiteUser siteUser = userService.getUser(principal.getName());
		
		answerService.vote(answer, siteUser);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		//해당 답변이 달린 원글로 이동
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/novote/{id}")
	public String answerNotVote(@PathVariable("id")Integer id, Principal principal) {
		
		Answer answer = answerService.getAnswer(id);
		
		SiteUser siteUser = userService.getUser(principal.getName());
		
		answerService.novote(answer, siteUser);
		
		return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
		//해당 답변이 달린 원글로 이동
	}
	
}