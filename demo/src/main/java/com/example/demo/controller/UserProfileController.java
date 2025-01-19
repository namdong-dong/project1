package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.service.UserProfileService;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.model.UserProfile;

@RestController
public class UserProfileController {

	private UserProfileMapper mapper;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private UserProfileService userProfileService;
	
	public UserProfileController(UserProfileMapper mapper, PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
	}
	
	@GetMapping("/user/{id}")
	public UserProfile getUserProfile(@PathVariable("id") String id) {
		return mapper.getUserProfile(id);
	}
	
	@GetMapping("/user/all")
	public List<UserProfile> getUserProfileList() {
		return mapper.getUserProfileList();
	}
	
	@PostMapping("/user/")
	public ResponseEntity<Map<String, String>> postUserProfile(@RequestBody UserProfile userProfile) {
		Map<String, String> response = new HashMap<>();
		if (userProfile.getPhone() == null) {
			userProfile.setPhone("N/A");
		}
		if (userProfile.getAddress() == null) {
			userProfile.setAddress("N/A");
		}
		String hashedPassword = passwordEncoder.encode(userProfile.getPw());
		System.out.println("Hashed Password: " + hashedPassword);
		mapper.insertUserProfile(userProfile.getId(), hashedPassword, userProfile.getName(), userProfile.getAddress(), userProfile.getPhone());
		response.put("redirectUrl", "/login");
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/user/{id}")
	public void putUserProfile(@PathVariable("id") String id, @RequestParam("pw") String pw, @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address) {
		mapper.updateUserProfile(id, pw, name, phone, address);
	}
	
	@DeleteMapping("/user/{id}")
	public void deleteUserProfile(@PathVariable("id") String id) {
		mapper.deleteUserProfile(id);
	}
	
	//중복 검사에 사용
	@GetMapping("/check-id")
	public boolean checkId(@RequestParam String id) {
		return userProfileService.isIdAvailable(id); // 사용 가능하면 true 반환
	}
	
	@PostMapping("login")
	public boolean login(@RequestBody Map<String, String> loginData) {
		String id = loginData.get("id");
		String pw = loginData.get("pw");
		String hashedPassword = mapper.getPasswordById(id);
		System.out.println(passwordEncoder.matches(pw, hashedPassword));
		System.out.println(passwordEncoder.matches(pw, hashedPassword));
		System.out.println(passwordEncoder.matches(pw, hashedPassword));
		// 비밀번호 비교
		return passwordEncoder.matches(pw, hashedPassword);
	}
	
}