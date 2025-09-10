package com.kkuk.japtest.kkukboard.question;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.kkuk.japtest.kkukboard.answer.AnswerForm;
import com.kkuk.japtest.kkukboard.user.SiteUser;
import com.kkuk.japtest.kkukboard.user.UserService;

import jakarta.validation.Valid;

@RequestMapping("/question") //prefix(접두사)
@Controller
public class QuestionController {
	
//	@Autowired
//	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping(value = "/list")
	//@ResponseBody
	public String list(Model model) {
		
		//List<Question> questionList = questionRepository.findAll(); //모든 질문글 불러오기
		List<Question> questionList = questionService.getList();
		//Page<Question> paging = questionService.getList(page);
		//게시글 10개씩 자른 리스트 -> 페이지당 10개 - 2페이지(11~20)
		model.addAttribute("questionList", questionList);
		
		return "question_list";
	}	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/detail/{id}") //파라미터이름 없이 값만 넘어왔을때 처리
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
		
		//service에 3을 넣어서 호출
		Question question = questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail"; //타임리프 html의 이름
	}
	
	
	@GetMapping(value = "/create") //질문 등록 폼만 매핑해주는 메서드->GET
	public String questionCreate(QuestionForm questionForm) {
		return "question_form"; //질문 등록하는 폼 페이지 이름
	}
	
//	@PostMapping(value = "/create") //질문 내용을 DB에 저장하는 메서드->POST
//	public String questionCreate(@RequestParam(value = "subject") String subject, @RequestParam(value = "content") String content) {
//		//@RequestParam("subject") String subject-> String subject = request.getParameter("subject")
//		//@RequestParam("content") String content-> String content = request.getParameter("content")
//		
//		questionService.create(subject, content); //질문 DB에 등록하기
//		
//		return "redirect:/question/list"; //질문 리스트로 이동->반드시 redirect
//	}
	@PreAuthorize("isAuthenticated()") 
	@PostMapping(value = "/create") //질문 내용을 DB에 저장하는 메서드->POST
	public String questionCreate(@Valid QuestionForm questionForm,Principal principal,BindingResult bindingResult) {
		//@RequestParam("subject") String subject-> String subject = request.getParameter("subject")
		//@RequestParam("content") String content-> String content = request.getParameter("content")
		
		if(bindingResult.hasErrors()) { //참이면 -> 유효성 체크에서 에러 발생
			return "question_form"; // 에러 발생시 질문등록폼으로 이동
		}
		
		SiteUser siteUser = userService.getUser(principal.getName());
		// 현재 로그인된 유저의 유저name 으로 entity 가져오기
		
		questionService.create(questionForm.getSubject(), questionForm.getContent(),siteUser); //질문 DB에 등록하기
		
		return "redirect:/question/list"; //질문 리스트로 이동->반드시 redirect
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/modify/{id}")
	public String questionModify(QuestionForm questionForm ,@PathVariable("id") Integer id, Principal principal) {
		Question question = questionService.getQuestion(id);//id에 해당하는 entity 반환
		
		//글 쓴 유저와 로그인한 유저의 동일여부를 다시 한번 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		//question_form에 questionForm의 subject와 content를 value로 출력하는 기능이 이미 있으므로
		//해당 폼을 재활ㅇ룡하기 위해 questionForm에 question의 필드값을 저장하여 전송
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/modify/{id}")
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
			Principal principal, @PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = questionService.getQuestion(id);
		//글 쓴 유저와 로그인한 유저의 동일여부를 다시 한번 검증
		if(!question.getAuthor().getUsername().equals(principal.getName())) { // 참이면 수정권한 없음
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
		}
		
		questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping(value = "/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id")Integer id) {
		Question question = questionService.getQuestion(id);
		if(!question.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		questionService.delete(question);
		return "redirect:/";
	}
	
	
}