package com.kh.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class jdbcUtil {
	
	/* 
	 * JDBC API사용중 -> 중복코드가 너무 많음
	 * 중복된 코드를 메서드로 분리하여 필요할 때 마다 '재사용'하자
	 * -> util 클래스의 목적
	 */
	
	// System.out.println((double)1 + 1.1);
	// 자료형이 다른 값 끼리는 연산이 불가능
	// 연산의 결과도 항상 같은 자료형으로 나와야함
	
	public static Connection getConnection() {
		
		final String URL = "jdbc:oracle:thin:@112.221.156.34:12345:XE";
		final String USERNAME = "KH08_KTY";
		final String PASSWORD = "KH1234";
		
		Connection conn = null;
		
		try {
			conn = DriverManager.getConnection(
					URL,USERNAME,PASSWORD);
		}catch(SQLException e	) {
			e.printStackTrace();
		} return conn;
	} 
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null);{
			stmt.close();
			}
		}catch(SQLException e) {
			System.out.println("PrepatedStatement 오류");
		}
	}
	
}
