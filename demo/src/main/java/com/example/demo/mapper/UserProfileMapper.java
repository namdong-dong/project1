package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.UserProfile;

@Mapper
public interface UserProfileMapper {
	@Select("SELECT * FROM UserProfile WHERE id=#{id}")
	UserProfile getUserProfile(@Param("id") String id);
	
	@Select("SELECT * FROM UserProfile")
	List<UserProfile> getUserProfileList();
	
	@Insert("INSERT INTO UserProfile (id, pw, name, phone, address, email, phoneCompany) VALUES(#{id}, #{pw}, #{name}, #{phone}, #{address}, #{email}, #{phoneCompany})")
	int insertUserProfile(@Param("id") String id, @Param("pw") String pw, @Param("name") String name, @Param("phone") String phone, @Param("address") String address, @Param("email") String email, @Param("phoneCompany") String phoneCompany);

	@Update("UPDATE UserProfile SET name=#{name}, phone=#{phone}, address=#{address}, email=#{email}, phoneCompany=#{phoneCompany} WHERE id=#{id}")
	int updateUserProfile(@Param("id") String id, @Param("name") String name, @Param("phone") String phone, @Param("address") String address, @Param("email") String email, @Param("phoneCompany") String phoneCompany);

	@Delete("DELETE FROM UserProfile WHERE id=#{id}")
	int deleteUserProfile(@Param("id") String id);
	
	//중복확인하는 매퍼 + 로그인할때도 사용
	@Select("SELECT COUNT(*) FROM UserProfile WHERE id = #{id}")
	int countById(String id);

	@Select("SELECT pw FROM UserProfile WHERE id = #{id}")
	String getPasswordById(String id);
	
	@Select("SELECT * FROM UserProfile WHERE id = #{id}")
	UserProfile findById(String id);
 
}
