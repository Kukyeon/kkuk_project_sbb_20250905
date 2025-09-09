package com.kkuk.japtest.kkukboard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@RequestMapping("/user")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/signup") // 회원가입 폼 띄우기
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}
	
	@PostMapping(value = "/signup") // 회원가입정보 DB 입력
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {//참이면 회원정보입력 에러발생 
			return "signup_form";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect","비밀번호가 일치하지않습니다.");
			return "signup_form";
		}	
		userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getPassword1());
			
		
		
		return "rediect:/"; //첫화면으로 이동
	}
	
}
