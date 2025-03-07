package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.AddressMapper;
import com.example.demo.mapper.EmailMapper;
import com.example.demo.mapper.PhoneMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.UserVO;

@Service
public class UserService {
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PhoneMapper phoneMapper;

	@Autowired
	private EmailMapper emailMapper;

	@Autowired
	private AddressMapper addressMapper;

	public List<UserVO> getUserList(String category, String keyword) {
		if (keyword == null || keyword.isEmpty()) {
			return mapper.getUserList();
		} else {
			return mapper.getSearchList(category, keyword);
		}
		
	}
	
	@Transactional
	public void registerUser(UserVO userVO) {
//		System.out.println("userVO : " + userVO.toString());
		
		// Phone 등록 및 pNO 반환
		phoneMapper.insertPhone(userVO.getPhoneVO());
		int phoneNo = userVO.getPhoneVO().getPhoneNo(); // 자동 반환된 pNO 사용
		System.out.println("Generated phoneNo: " + phoneNo);

		// Email 등록 및 eNO 반환
		emailMapper.insertEmail(userVO.getEmailVO());
		int emailNo = userVO.getEmailVO().getEmailNo();

		// Address 등록 및 aNO 반환
		addressMapper.insertAddress(userVO.getAddressVO());
		int addressNo = userVO.getAddressVO().getAddressNo();

		// 반환된 pNO, eNO, aNO를 User 테이블에 삽입
		userVO.getPhoneVO().setPhoneNo(phoneNo);
		userVO.getEmailVO().setEmailNo(emailNo);
		userVO.getAddressVO().setAddressNo(addressNo);

		mapper.registerUser(userVO); // 최종적으로 User 테이블에 등록
	}

	public UserVO getUserInfo(int userNO) {
		return mapper.getUserInfo(userNO);
	}
	
	@Transactional
	public void modifyUserInfo(UserVO userVO) {
		mapper.updateUser(userVO);
		phoneMapper.updatePhone(userVO.getPhoneVO());
		emailMapper.updateEmail(userVO.getEmailVO());
		addressMapper.updateAddress(userVO.getAddressVO());
	}
	
	@Transactional
	public boolean deleteUserInfo(int userNo) {
		UserVO userVO = mapper.getUserInfo(userNo);
			if (userVO == null) {
				return false; // 회원이 존재하지 않음
			}
		int deleted = mapper.deleteUser(userNo);
		return deleted > 0 ;
	}

}