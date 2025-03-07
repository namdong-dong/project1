package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;



@Controller
public class HomeController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String main(Model model) {
//		model.addAttribute("title", "고객 정보 관리");
		return "index";
	}
	
	@GetMapping("/user/list")
	public String userList() {
		return "list";
	}
	
	@GetMapping("/user/join")
	public String userJoin() {
		return "join";
	}
	
	@PostMapping("/user/info")
	public String userInfo(@RequestParam("userNo") int userNo, Model model) {
		UserVO user = userService.getUserInfo(userNo);
		model.addAttribute("user", user);
		return "userInfo";
	}
//	@GetMapping("/users/join")
//	public String join() {
//		return "join.html";
//	}
//	
//	@GetMapping("/auth/login")
//	public String login() {
//		return "login.html";
//	}
//	
//	@GetMapping("/users/modify")
//	public String modify() {
//		return "modify.html";
//	}
}
