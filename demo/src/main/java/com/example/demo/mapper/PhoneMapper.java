package com.example.demo.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Update;

import com.example.demo.vo.PhoneVO;

@Mapper
public interface PhoneMapper {
    // 전화번호 삽입 후 자동 생성된 pNO 반환
    @Insert("INSERT INTO phone (phoneNumber, company, phoneState) VALUES (#{phoneNumber}, #{company}, #{phoneState})")
    @Options(useGeneratedKeys = true, keyProperty = "phoneNo", keyColumn = "phoneNo") 
    void insertPhone(PhoneVO phoneVO);  
    
    @Update("UPDATE phone "
            + "SET phoneNumber = #{phoneNumber}, "
            + " company = #{company}, "
            + " phoneState = #{phoneState} "
            + " WHERE phoneNo = (SELECT phoneNo FROM user WHERE userNo = #{userNo})")
    void updatePhone(PhoneVO phoneVO);
    
    @Delete("DELETE FROM phone WHERE phoneNo = #{phoneNo}")
    void deletePhone(int userNo);
}