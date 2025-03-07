package com.example.demo.mapper;
import org.apache.ibatis.annotations.*;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.UserProfile;
import com.example.demo.vo.UserVO;

@Mapper
public interface UserMapper {
	
	
	@Select("SELECT * FROM UserProfile WHERE id=#{id}")
	UserProfile getUserProfile(@Param("id") String id);
	
	@Select("SELECT u.userNo, u.name, u.gender, u.birthday, u.joinDate, u.lastLogin, u.state, "
			+ "p.phoneNo AS phoneNo, p.phoneNumber as phoneNumber, p.company as company, p.phoneState as phoneState, "
			+ "e.emailNo AS emailNo, e.email as email, e.emailState as emailState, "
			+ "a.addressNo AS adressNo, a.address as address, a.zip as zip " 
			+ " FROM user u"
			+ " LEFT JOIN phone p ON u.phoneNo = p.phoneNo"
			+ " LEFT JOIN email e ON u.emailNo = e.emailNo"
			+ " LEFT JOIN address a ON u.addressNo = a.addressNo")
	@Results(id = "UserResultMap", value = {
			@Result(property = "userNo", column = "userNo"),
			@Result(property = "name", column = "name"),
			@Result(property = "gender", column = "gender"),
			@Result(property = "birthday", column = "birthday"),
			@Result(property = "joinDate", column = "joinDate"),
			@Result(property = "lastLogin", column = "lastLogin"),
			@Result(property = "state", column = "state"),
			
			// PhoneVO 매핑
			@Result(property = "phoneVO.phoneNo", column = "phoneNo"),
			@Result(property = "phoneVO.phoneNumber", column = "phoneNumber"),
			@Result(property = "phoneVO.company", column = "company"),
			@Result(property = "phoneVO.phoneState", column = "phoneState"),
			
			// EmailVO 매핑
			@Result(property = "emailVO.emailNo", column = "emailNo"),
			@Result(property = "emailVO.email", column = "email"),
			@Result(property = "emailVO.emailState", column = "emailState"),
			
			// AddressVO 매핑
			@Result(property = "addressVO.addressNo", column = "addressNo"),
			@Result(property = "addressVO.address", column = "address"),
			@Result(property = "addressVO.zip", column = "zip")
		})
	List<UserVO> getUserList();
	
	@SelectProvider(type = UserSqlProvider.class, method = "getSearchList")
	@ResultMap("UserResultMap")
	List<UserVO> getSearchList(@Param("category") String category, @Param("keyword") String keyword);
	
//	@Insert("INSERT INTO UserProfile (id, pw, name, phone, address, email, phoneCompany) VALUES(#{id}, #{pw}, #{name}, #{phone}, #{address}, #{email}, #{phoneCompany})")
//	int insertUserProfile(@Param("id") String id, @Param("pw") String pw, @Param("name") String name, @Param("phone") String phone, @Param("address") String address, @Param("email") String email, @Param("phoneCompany") String phoneCompany);
	
//	@Insert("INSERT INTO user (name, gender, birthday, joinDate, lastLogin) VALUES(#{name}, #{gender}, #{birthday}, now(), now())")
//	int insertNewUser(@Param("name") String name, @Param("gender") String gender, @Param("birthday") String birthday);
	
	@Insert("INSERT INTO user (name, gender, birthday, joinDate, lastLogin, phoneNo, emailNo, addressNo) VALUES(#{name}, #{gender}, #{birthday}, now(), now(), #{phoneVO.phoneNo}, #{emailVO.emailNo}, #{addressVO.addressNo})")
	int registerUser(UserVO userVO);
	
//	@Update("UPDATE UserProfile SET name=#{name}, phone=#{phone}, address=#{address}, email=#{email}, phoneCompany=#{phoneCompany} WHERE id=#{id}")
//	int updateUserProfile(@Param("id") String id, @Param("name") String name, @Param("phone") String phone, @Param("address") String address, @Param("email") String email, @Param("phoneCompany") String phoneCompany);
//
//	@Delete("DELETE FROM UserProfile WHERE id=#{id}")
//	int deleteUserProfile(@Param("id") String id);
	
//	@Select("SELECT COUNT(*) FROM UserProfile WHERE id = #{id}")
//	int countById(String id);

//	@Select("SELECT pw FROM UserProfile WHERE id = #{id}")
//	String getPasswordById(String id);
	
//	@Select("SELECT * FROM UserProfile WHERE id = #{id}")
//	UserProfile findById(String id);

	@Select("SELECT u.userNo, u.name, u.gender, u.birthday, u.joinDate, u.lastLogin, u.state, "
			+ "p.phoneNo AS phoneNo, p.phoneNumber as phoneNumber, p.company as company, p.phoneState as phoneState, "
			+ "e.emailNo AS emailNo, e.email as email, e.emailState as emailState, "
			+ "a.addressNo AS addressNo, a.address as address, a.zip as zip "
			+ " FROM user u "
			+ " LEFT JOIN phone p ON u.phoneNo = p.phoneNo "
			+ " LEFT JOIN email e ON u.emailNo = e.emailNo "
			+ " LEFT JOIN address a ON u.addressNo = a.addressNo "
			+ " WHERE u.userNo = #{userNo}")
	@Results({
		@Result(column = "userNo", property = "userNo"),
		@Result(column = "name", property = "name"),
		@Result(column = "gender", property = "gender"),
		@Result(column = "birthday", property = "birthday"),
		@Result(column = "joinDate", property = "joinDate"),
		@Result(column = "lastLogin", property = "lastLogin"),
		@Result(column = "state", property = "state"),
		// 📌 emailVO 매핑
		@Result(column = "emailNo", property = "emailVO.emailNo"),
		@Result(column = "email", property = "emailVO.email"),
		@Result(column = "emailState", property = "emailVO.emailState"),
		// 📌 phoneVO 매핑
		@Result(column = "phoneNo", property = "phoneVO.phoneNo"),
		@Result(column = "phoneNumber", property = "phoneVO.phoneNumber"),
		@Result(column = "company", property = "phoneVO.company"),
		@Result(column = "phoneState", property = "phoneVO.phoneState"),
		// 📌 addressVO 매핑
		@Result(column = "addressNo", property = "addressVO.addressNo"),
		@Result(column = "address", property = "addressVO.address"),
		@Result(column = "zip", property = "addressVO.zip")
	})
	UserVO getUserInfo(int userNo);

	@Update("UPDATE user SET name = #{name}, gender = #{gender}, birthday = #{birthday}, state = #{state} WHERE userNo = #{userNo}")
	void updateUser(UserVO userVO);

	@Delete("DELETE FROM user WHERE userNo = #{userNo}")
	int deleteUser(int userNo);
}
