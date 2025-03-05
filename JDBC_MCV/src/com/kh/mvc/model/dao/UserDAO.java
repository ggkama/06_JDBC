package com.kh.mvc.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.mvc.model.dto.UserDTO;

/**
 * DAO(Data Access Object)
 * 
 * 데이터베이스 관련된 작업(CRUD)을 전문적으로 담당하는 객체
 * DAO안에 모든 메소드들은 데이터베이스와 관련된 기능으로 만들 것
 * 
 * Controller를 통해 호출된 기능을 수행
 * DB에 직접 접근한 후 SQL문을 수행하고 결과 받기(JDBC)
 */

public class UserDAO {
	
	/* 
	 * JDBC용 객체
	 * 
	 * - Connection : DB와의 연결정보를 담고있는 객체(IP주소, Port, 사용자명, 비번)
	 * - Statement : Connection이 가지고 있는 연결정보 DB에
	 * 							 SQL문을 전달하고 실행하고 결과도 받아오는 객체
	 * - ResultSet : 실행한 SQL문이 SELECT문일 경우 
	 * 							 조회된 결과가 처음 담기는 객체
	 * 
	 * - PreparedStatement : SQL문을 미리 준비하는 개념
	 * 											 ?(PlaceHolder)로 확보해놓은 공간을
	 *											 사용자가 입력한 값들과 바인딩해서 SQL문을 수행
	 * 
	 * ** 처리절차
	 * 
	 * 1) JDBC Driver등록 : 해당 DBMC에서 제공하는 클래스를 동적을 등록
	 * 
	 * 
	 * 2) Connection 객체 생성 : 접속하고자하는 DB의 정보를 입력해서 생성
	 * 												(URL,사용자이름,비밀번호)
	 * 
	 * 
	 * 3-1) PreparedStatement 객체 생성 : 
	 * 			Connection 객체를 이용해서 생성(미완성된 SQL문 미리 전달)
	 * 
	 * 3-2) 미완성된 SQL문을 완성형태로 만들어주어야 함
	 *    => 미완성된 경우에만 해당/ 완성된 경우에는 생략
	 *    
	 *    
	 * 4) SQL문을 실행 : executeXXX() => SQL을 인자로 전달하지 않음
	 * 							 > SELECT(DQL) : executeQuery()
	 * 							 > DML	  		 : executeUpdate() 
	 * 
	 * 
	 * 5) 결과 받기 : 
	 * 					 > SELECT : ResultSet타입 객체(조회데이터담김)
	 *					 > DML    : int(처리된 행의 개수)
	 *
	 *
	 * 6-1) ResultSet에 담겨있는 데이터들을 하나하나씩 뽑아서 DTO객체 필드에
	 *  		옮겨담은 후 조회 결과가 여러행일 경우 List로 관리
	 *  
	 * 6-2) 트랜잭션 처리 -> ROLLBACK , COMMIT 처리
	 * 
	 * 7)(생략될 수 있음) 자원반납 (close메서드 호출) => 생성의 역순으로
	 * 
	 * 8) 결과 반환 -> Controller로 반환
	 * 							SELECT -> 6-1에서 만든것
	 * 						  DML : 처리된 행의 개수
	 */
	
	private final String URL = "jdbc:oracle:thin:@112.221.156.34:12345:XE";
	private final String USERNAME = "KH08_KTY";
	private final String PASSWORD = "KH1234";
	
	
	
	static {
		
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("ojdbc");
			
		}

	} // static 블럭 == 맨 첫번째로 한번만 실행
	
	public List<UserDTO> findAll(Connection conn) {
		
		// DB로 이동
		/* 
		 * VO / DTO / Entity  -- 테이블에있는 한 행의 데이터를 담기위해서 사용
		 * 
		 * 테이블에 있는 한 행의 데이터를 담는 것
		 * 
		 * 1명의 회원정보는 1개의 UserDTO객체를 필드에 값을 담아야함.
		 * 
		 * 문제점 : userDTO가 몇개가 나올지 알 수 없음
		 */
		
		// 추상적 자료형 List -> 순서를 가지고 일렬로 나열한 원소의 모임
		// 수학적으로 정의한 것.
		
		List<UserDTO> list = new ArrayList();
		String sql = """
				SELECT  
					USER_NO,  
					USER_ID, 
					USER_PW, 
					USER_NAME, 
					ENROLL_DATE 
				FROM 
					TB_USER 
				ORDER BY 
				ENROLL_DATE DESC 
				""";
		
		// Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rset = null;
		
		
		try {
//			conn= DriverManager.getConnection(
//					"jdbc:oracle:thin:@112.221.156.34:12345:XE",
//					"KH08_KTY","KH1234");
			
			stmt = conn.prepareStatement(sql);
			rset = stmt.executeQuery();
			while(rset.next()) { // if사용시 얼마나 많은 데이터를 가져올지 모르기 때문에
				UserDTO user = new UserDTO();
				
				user.setUserNo(rset.getInt("USER_NO"));
				user.setUserId(rset.getString("USER_ID"));
				user.setUserPw(rset.getString("USER_PW"));
				user.setUserName(rset.getString("USER_NAME"));
				user.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(user);
			}
			
			
		} catch (SQLException e) {
			System.out.println("오타확인");
		} finally {
			
			try {
				if(rset != null) {
					rset.close();
				}
			} catch (SQLException e) {
				System.out.println("ResultSet 오류");
				
			} // 여기다가 작성
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				System.out.println("Connection 오류");
			}
			
		}
		// finally 안에 종료 구문 넣는 이유
		// -> 유지보수에 도움
		
		return list;
		
	}

	/**
	 * @param user 사용자가 입력한 아이디 / 비밀번호 / 이름이 각각 필드에 대입되어있음
	 * @return 
	 * @return 아직 어떤것을 돌려줄지 정하지 않음
	 */
	public int insertUser(UserDTO user) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = """
				INSERT INTO 
					TB_USER 
				VALUES 
				(SEQ_USER_NO.NEXTVAL, 
				 ?, 
				 ?, 
				 ?, 
				 SYSDATE) 
				""";
	
		int result = 0;
		try {
			
		conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@112.221.156.34:12345:XE",
					"KH08_KTY","KH1234");

		
		//		오토커밋 x
		//		conn.setAutoCommit(false);
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, user.getUserId());
		pstmt.setString(2, user.getUserPw());
		pstmt.setString(3, user.getUserName());
		
		result = pstmt.executeUpdate()	;
		
		
		} catch(SQLException e) {
			e.printStackTrace();
			} finally {
			 try{
					if(pstmt != null) pstmt.close();
			}catch(SQLException e) {
					e.printStackTrace();
			}try {
					if(conn != null) conn.close();
			}catch(SQLException e){
					e.printStackTrace();
			}
	}
	 return result;
	
	}
}
