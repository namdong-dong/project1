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
	public ResponseEntity<Map<String, String>> registerUser(
			@RequestPart("userData") String userData,
			@RequestPart(value = "img", required = false) MultipartFile file) {
		
		Map<String, String> response = new HashMap<>();

		try {
			// JSON 데이터를 객체로 변환
			UserVO userVO = objectMapper.readValue(userData, UserVO.class);
			String fileName = null;

			// 📌 파일이 존재하는 경우만 처리
			if (file != null && !file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();
				
				// 파일명 검증
				if (originalFileName == null || !originalFileName.contains(".")) {
					response.put("message", "잘못된 파일 형식입니다.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// 파일 확장자 검증
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
				if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
					response.put("message", "허용되지 않은 파일 형식입니다.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// 고유한 파일명 생성
				fileName = UUID.randomUUID().toString() + fileExtension;

				// 저장 경로 설정
				Path uploadDir = Paths.get("src", "main", "resources", "static", "uploads");
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}

				// 파일 저장
				Path filePath = uploadDir.resolve(fileName).toAbsolutePath().normalize();
				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("📂 파일 저장 완료: " + filePath.toString());
				} catch (IOException e) {
					response.put("message", "파일 저장 실패: " + e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
				}
			}

			// 파일명이 존재하면 DB에 저장
			userVO.setImg(fileName);

			// 사용자 등록
			userService.registerUser(userVO);

			response.put("message", "회원이 성공적으로 등록되었습니다.");
			response.put("redirectUrl", "/user/list");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "회원 등록 실패: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	
	@PutMapping(value="/{userNo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, String>> modifyUserInfo(
			@RequestPart("userData") String userData,
			@RequestPart(value = "img", required = false) MultipartFile file,
			@PathVariable int userNo) {

		Map<String, String> response = new HashMap<>();

		try {
			// JSON 데이터를 UserVO 객체로 변환
			UserVO userVO = objectMapper.readValue(userData, UserVO.class);
			userVO.setUserNo(userNo); // URL에서 가져온 userNo 설정

			String fileName = userService.hasImg(userNo); // 기존 파일명 유지
			Path uploadDir = Paths.get("src", "main", "resources", "static", "uploads");

			// 📌 파일이 존재하면 삭제 후 새로 저장
			if (file != null && !file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();

				if (originalFileName == null || !originalFileName.contains(".")) {
					response.put("message", "잘못된 파일 형식입니다.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// 파일 확장자 검증
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				List<String> allowedExtensions = Arrays.asList(".jpg", ".jpeg", ".png", ".gif");
				if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
					response.put("message", "허용되지 않은 파일 형식입니다.");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}

				// ✅ 기존 파일 삭제 (기존 파일이 존재하면 삭제)
				System.out.println("기존 파일 : " + fileName.toString());
				if (fileName != null && !fileName.isEmpty()) {
					Path oldFilePath = uploadDir.resolve(fileName);
					if (Files.exists(oldFilePath)) {
						Files.delete(oldFilePath);
						System.out.println("기존 파일 삭제 완료: " + oldFilePath.toString());
					}
				}

				// 새 파일명 생성
				fileName = UUID.randomUUID().toString() + fileExtension;

				// 업로드 폴더가 없으면 생성
				if (!Files.exists(uploadDir)) {
					Files.createDirectories(uploadDir);
				}

				// 파일 저장
				Path filePath = uploadDir.resolve(fileName).toAbsolutePath().normalize();
				try (InputStream inputStream = file.getInputStream()) {
					Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("새 파일 저장 완료: " + filePath.toString());
				} catch (IOException e) {
					response.put("message", "파일 저장 실패: " + e.getMessage());
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
				}
			}

			// 최종 파일명을 userVO에 반영
			userVO.setImg(fileName);

			// 사용자 정보 업데이트
			userService.modifyUserInfo(userVO);

			response.put("message", "회원정보가 성공적으로 수정되었습니다.");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace(); // ✅ 예외 발생 시 콘솔에 로그 남기기
			response.put("message", "회원 수정 실패: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
	@Transactional
	@DeleteMapping("/{userNo}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int userNo) throws java.io.IOException {
		UserVO userVO = userService.getUserInfo(userNo);
		String fileName = userService.hasImg(userNo);
		Map<String, String> response = new HashMap<>();
		
		Path uploadDir = Paths.get("src", "main", "resources", "static", "uploads");
		if (userVO.getImg() != null) {
			Path oldFilePath = uploadDir.resolve(fileName);
			if (Files.exists(oldFilePath)) {
				Files.delete(oldFilePath);
			}
		}
		boolean deleted = userService.deleteUserInfo(userNo);
		if (deleted) {
			response.put("message", "회원이 성공적으로 삭제되었습니다.");
			return ResponseEntity.ok(response);
		} else {
			response.put("message", "해당 회원이 존재하지 않습니다.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
	
}
