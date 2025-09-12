package com.kkuk.japtest.kkukboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping(value = "/") //클라우드용 요청처리
	//@GetMapping(value = "/")//root 요청 처리 (로컬용)
	public String root() {
		return "redirect:/question/list";
	}
	
}
