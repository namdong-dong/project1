package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/list")
	public List<UserVO> getUserList(@RequestParam(required = false) String keyword, @RequestParam(required = false) String category) {
		return userService.getUserList(category, keyword);
	}
	
	@GetMapping("/{userNo}")
	public ResponseEntity<UserVO> getUserInfo(@PathVariable int userNo) {
		UserVO userVO = userService.getUserInfo(userNo);
		if (userVO != null) {
			return ResponseEntity.ok(userVO); // 정상 응답 (200 OK)
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 유저 없으면 404 응답
		}
	}
	
	@PostMapping("/join")
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserVO userVO) {
		Map<String, String> response = new HashMap<>();
		userService.registerUser(userVO);
		response.put("message", "회원이 성공적으로 등록되었습니다.");
		response.put("redirectUrl", "/user/list");
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{userNo}")
	public ResponseEntity<Map<String, String>> modifyUserInfo(@RequestBody UserVO userVO, @PathVariable int userNo) {
		userVO.setUserNo(userNo); // URL에서 가져온 userNo를 설정
		userService.modifyUserInfo(userVO);
		Map<String, String> response = new HashMap<>();
		response.put("message", "회원정보가 성공적으로 수정되었습니다.");
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{userNo}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int userNo) {
		boolean deleted = userService.deleteUserInfo(userNo);
		Map<String, String> response = new HashMap<>();
		if (deleted) {
			response.put("message", "회원이 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "해당 회원이 존재하지 않습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	
}
