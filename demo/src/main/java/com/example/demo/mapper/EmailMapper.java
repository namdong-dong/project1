package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.EmailVO;

@Mapper
public interface EmailMapper {
	// 
		@Insert("INSERT INTO email (email, emailState) VALUES (#{email}, #{emailState})")
		@Options(useGeneratedKeys = true, keyProperty = "emailNo")
		void insertEmail(EmailVO emailVO);  // 여기서 Phone 객체에 pNO가 자동으로 세팅됩니다.
		
		@Update("UPDATE email "
				+ "SET email = #{email},"
				+ " emailState = #{emailState}"
				+ " WHERE emailNo = (SELECT emailNo from user WHERE userNo = #{userNo})")
		void updateEmail(EmailVO emailVO);
		
		@Delete("DELETE FROM email WHERE emailNo = #{emailNo}")
		void deleteEmail(int userNo);
}
