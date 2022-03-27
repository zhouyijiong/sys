package com.zyj.disk.controller.page;

import com.zyj.disk.sys.annotation.verify.Access;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController{
	private static final String USER = "management/user/b78ba6e0a8460310bc11b9daaa826a2c";

	@GetMapping
	public String index(){
		return "index/index";
	}

	@Access
	@GetMapping("/management")
	public String management(){
		return USER;
	}
}