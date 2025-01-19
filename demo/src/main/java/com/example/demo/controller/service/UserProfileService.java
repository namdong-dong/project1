package com.example.demo.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.example.demo.mapper.UserProfileMapper;

@Service
public class UserProfileService {
	@Autowired
	private final UserProfileMapper userProfileMapper;

	public UserProfileService(UserProfileMapper userProfileMapper) {
		this.userProfileMapper = userProfileMapper;
	}

	public boolean isIdAvailable(String id) {
		return userProfileMapper.countById(id) == 0; // 사용 가능하면 true 반환
	}
	
	public boolean login(String id, String pw) {
		// ID 존재 여부 확인
		int count = userProfileMapper.countById(id);
		if (count == 0) {
			return false; // ID 없음
		}

		// DB에서 암호화된 비밀번호 가져오기
		String hashedPassword = userProfileMapper.getPasswordById(id);

		// 입력된 비밀번호와 해싱된 비밀번호 비교
		return BCrypt.checkpw(pw, hashedPassword);
	}
}