package com.example.demo.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private UserService userService;
	private ObjectMapper objectMapper;
	
	public UserController(UserService userService, ObjectMapper objectMapper) {
		this.userService = userService;
		this.objectMapper = objectMapper;
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
	
	@PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, String>> registerUser(@RequestPart("userData") String userData, @RequestPart(value = "img", required = false) MultipartFile file) {
		Map<String, String> response = new HashMap<>();
		try {
		UserVO userVO = objectMapper.readValue(userData, UserVO.class);
		// 파일 저장
		String originalFileName = file.getOriginalFilename();
		String fileName = null;
		String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
		List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
		if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
		    throw new IllegalArgumentException("허용되지 않은 파일 형식입니다.");
		}
		if (file != null && !file.isEmpty()) {
			fileName = UUID.randomUUID()+fileExtension;
			Path uploadDir = Paths.get("uploads");
			if (!Files.exists(uploadDir)) {
				Files.createDirectories(uploadDir);
			}
			Path filePath = uploadDir.resolve(fileName).toAbsolutePath().normalize();
			try (InputStream inputStream = file.getInputStream()) {
			    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			    System.out.println("파일 저장 완료: " + filePath.toString()); // ✅ 파일 저장 확인 로그
			} catch (IOException e) {
			    response.put("message", "파일 저장 실패: " + e.getMessage());
			    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}
		}
		
		userVO.setImg(fileName);
		userService.registerUser(userVO);
		
		response.put("message", "회원이 성공적으로 등록되었습니다.");
		response.put("redirectUrl", "/user/list");
		return ResponseEntity.ok(response);
		} catch (Exception e) {
	        response.put("message", "회원 등록 실패: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}
	
	@PutMapping("/{userNo}")
	public ResponseEntity<Map<String, String>> modifyUserInfo(@RequestBody UserVO userVO, @PathVariable int userNo) {
		userVO.setUserNo(userNo); // URL에서 가져온 userNo를 설정
		userService.modifyUserInfo(userVO);
		Map<String, String> response = new HashMap<>();
		response.put("message", "회원정보가 성공적으로 수정되었습니다.");
		return ResponseEntity.ok(response);
	}
	
	@Transactional
	@DeleteMapping("/{userNo}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int userNo) {
		UserVO userVO = userService.getUserInfo(userNo);
		 if (userVO.getImg() != null) {
				File file = new File("uploads/" + userVO.getImg());
				if (file.exists()) {
					file.delete();
				}
			}
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
