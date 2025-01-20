package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
	@Select("SELECT pw FROM UserProfile WHERE id = #{id}")
	String getPasswordById(String id);
}
