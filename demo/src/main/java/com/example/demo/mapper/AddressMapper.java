package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.AddressVO;

@Mapper
public interface AddressMapper {
	// 전화번호 삽입 후 자동 생성된 aNO 반환
	@Insert("INSERT INTO address (address, zip) VALUES (#{address}, #{zip})")
	@Options(useGeneratedKeys = true, keyProperty = "addressNo")
	void insertAddress(AddressVO addressVO);
	
	@Update("UPDATE address "
			+ "SET address = #{address},"
			+ " zip = #{zip}"
			+ " WHERE addressNo = (SELECT addressNo from user WHERE userNo = #{userNo})")
	void updateAddress(AddressVO addressVO);
	
	@Delete("DELETE FROM address WHERE addressNo = #{addressNo}")
	void deleteAddress(int userNo);
}
