package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mapper.LoginMapper;
import com.example.demo.model.UserProfile;
import com.example.demo.service.UserProfileService;
import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class LoginController {
	private final PasswordEncoder passwordEncoder;
	private LoginMapper mapper;
	private final JwtUtil jwtUtil;
	@Autowired
	private UserProfileService userProfileService;
	private List<String> blacklistedTokens = new ArrayList<>();
	public LoginController(LoginMapper mapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
		String id = loginData.get("id");
		String pw = loginData.get("pw");
//		System.out.println(id);
//		System.out.println(pw);
		String hashedPassword = mapper.getPasswordById(id);
		Map<String, Object> claim = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		if (passwordEncoder.matches(pw, hashedPassword)) {
//			System.out.println("Password matched");
			UserProfile userProfile = userProfileService.findById(id);
//			System.out.println(userProfile);
			claim.put("name", userProfile.getName());
			claim.put("phone", userProfile.getPhone());
			claim.put("address", userProfile.getAddress());
			String token = jwtUtil.generateToken(id); // JWT 생성
			response.put("success", true);
	        response.put("token", token);
	        response.put("claim", claim);
		} else {
			response.put("success", false);
	        response.put("message", "잘못된 로그인 정보입니다");
        }
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        // "Bearer " 접두사 제거
        String cleanedToken = token.replace("Bearer ", "");
        blacklistedTokens.add(cleanedToken); // 블랙리스트에 추가
        return ResponseEntity.ok().build();
    }
}

