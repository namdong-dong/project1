package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider implements ProviderMethodResolver {
	public String getSearchList(@Param("category") String category, @Param("keyword") String keyword) {
		return new SQL() {{
			SELECT("u.userNo, u.name, u.gender, u.birthday, u.joinDate, u.lastLogin, u.state");
			SELECT("p.phoneNo AS phoneNo, p.phoneNumber AS phoneNumber, p.company AS company, p.phoneState AS phoneState");
			SELECT("e.emailNo AS emailNo, e.email AS email, e.emailState AS emailState");
			SELECT("a.addressNo AS addressNo, a.address AS address, a.zip AS zip");
			FROM("user u");
			LEFT_OUTER_JOIN("phone p ON u.phoneNo = p.phoneNo");
			LEFT_OUTER_JOIN("email e ON u.emailNo = e.emailNo");
			LEFT_OUTER_JOIN("address a ON u.addressNo = a.addressNo");

			// 허용된 컬럼 목록만 사용하도록 필터링
			switch (category) {
				case "u.name":
					WHERE("u.name LIKE CONCAT('%', #{keyword}, '%')");
					break;
				case "u.gender":
					WHERE("u.gender LIKE CONCAT('%', #{keyword}, '%')");
					break;
				case "p.phoneNumber":
					WHERE("p.phoneNumber LIKE CONCAT('%', #{keyword}, '%')");
					break;
				case "e.email":
					WHERE("e.email LIKE CONCAT('%', #{keyword}, '%')");
					break;
				case "u.state":
					WHERE("u.state LIKE CONCAT('%', #{keyword}, '%')");
					break;
				default:
					throw new IllegalArgumentException("Invalid category: " + category);
			}
		}}.toString();
	}
}
