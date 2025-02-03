package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.model.UserProfile;
import com.example.demo.service.UserProfileService;
import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/user")
public class UserProfileController {

	private UserProfileMapper mapper;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	private UserProfileService userProfileService;
	private JwtUtil jwtUtil;
	
	public UserProfileController(JwtUtil jwtUtil, UserProfileMapper mapper, PasswordEncoder passwordEncoder) {
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
	}
	
//	@GetMapping("/{id}")
//	public UserProfile getUserProfile(@PathVariable("id") String id) {
//		return mapper.getUserProfile(id);
//	}
	
//	@GetMapping("/all")
//	public List<UserProfile> getUserProfileList() {
//		return mapper.getUserProfileList();
//	}
	
	@PostMapping("/")
	public ResponseEntity<Map<String, String>> postUserProfile(@RequestBody UserProfile userProfile) {
		Map<String, String> response = new HashMap<>();
		String id = userProfile.getId();
		if (!id.matches("^[a-zA-Z0-9]+$")) {
			response.put("success", "false");
	        response.put("message", "아이디는 영어와 숫자만 입력할 수 있습니다.");
	        return ResponseEntity.badRequest().body(response);
	    }
		if (userProfile.getPhone() == null) {
			userProfile.setPhone("");
		}
		if (userProfile.getAddress() == null) {
			userProfile.setAddress("");
		}
		if (userProfile.getEmail() == null) {
			userProfile.setEmail("");
		}
		String hashedPassword = passwordEncoder.encode(userProfile.getPw());
//		System.out.println("Hashed Password: " + hashedPassword);
		mapper.insertUserProfile(userProfile.getId(), hashedPassword, userProfile.getName(), userProfile.getPhone(), userProfile.getAddress(), userProfile.getEmail(), userProfile.getPhoneCompany());
		response.put("redirectUrl", "/auth/login");
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Map<String, String>> putUserProfile(@PathVariable("id") String id,@RequestBody Map<String, String> updates) {
		Map<String, String> response = new HashMap<>();
	    String name = updates.get("name");
	    String phone = updates.get("phone");
	    String address = updates.get("address");
	    String email = updates.get("email");
	    String phoneCompany = updates.get("phoneCompany");
		mapper.updateUserProfile(id, name, phone, address, email, phoneCompany);
		response.put("redirectUrl", "/");
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> deleteUserProfile(@PathVariable("id") String id) {
		Map<String, String> response = new HashMap<>();
		mapper.deleteUserProfile(id);
		response.put("redirectUrl", "/");
		return ResponseEntity.ok(response);
	}
	
	//중복 검사에 사용
	@GetMapping("/check-id")
	public boolean checkId(@RequestParam String id) {
		return userProfileService.isIdAvailable(id); // 사용 가능하면 true 반환
	}
	
	@GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        // 1. Bearer 토큰에서 "Bearer " 제거
        String jwtToken = token.replace("Bearer ", "");

        // 2. JWT에서 사용자 ID 추출
        String userId = jwtUtil.extractSubject(jwtToken);

        // 3. DB에서 사용자 정보 조회
        UserProfile user = userProfileService.findById(userId);

        // 4. 사용자가 없으면 404 반환
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // 5. 사용자 정보 반환
        return ResponseEntity.ok(user);
    }
	
}