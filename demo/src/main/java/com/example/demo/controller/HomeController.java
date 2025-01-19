package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

	@GetMapping("/")
	public String main() {
		return "index.html";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join.html";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	
}
