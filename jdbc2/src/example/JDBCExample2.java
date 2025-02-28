package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JDBCExample2 {
	public static void main(String[] args) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		/* java.sql.PreparedStatement(준비된 Statement)
		 * 
		 * - 전달하려는 SQL 중간이 ?(placeholder)를 작성히여
		 *  ? 자리에 java 값을 대입할 준비가
		 *  되어있는 Statement
		 *  
		 *  장접 1 : 값 추가가 간단해짐
		 *  
		 *  장점 2 : ? 값 대입 시 자료형에 맞는 리터럴 표기법으로
		 *          자동 변환된다.
		 *  
		 *  ex) ?에 int 대입 -> NUMBER 타입 리터럴(10,20,123)
		 *  ex) ?에 String 대입 -> CHAR 타입 리터럴 ('123')
		 *  										 (양쪽에 '' 자동추가)
		 *  
		 *  장점 3 : Statement의 문제점인 SQL Injection을 방지할 수 있음
		 *  -> Statement는 문자열 결합 방식으로 SQL을 만듦
		 *   -> 결합되는 문자열이 SQL 명령어 또는 '값' 모두 가능
		 *  
		 *  PreparedStatement는
		 *  ?를 이용해서 파라미터(값)만 연결하기 때문에
		 *  SQL Injection을 방지할 수 있음
		 */
		
		try { 
			// 커넥션 생성
			Class.forName("oracle.jdbc.OracleDriver");
			String url = "jdbc:oracle:thin:@112.221.156.34:12345:XE";
			String userName = "KH08_KTY"; 
			String password = "KH1234"; 
			conn = DriverManager.getConnection(url,userName,password);
			
			// 자동 커밋 쓰기
			conn.setAutoCommit(false);
			
			// SQL 작성
			Scanner sc = new Scanner(System.in);
			System.out.println("아이디 입력 : ");
			String id = sc.next();

			System.out.println("비밀번호 입력 : ");
			String pw = sc.next();

			System.out.println("이름 입력 : ");
			String name = sc.next();
			
			String sql = 
					"INSERT INTO TB_USER VALUES(SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)";
			
			/* DML(INSERT,UPDATE,DELETE)시 AutoCommit 끄기 
			 * -> Connection을 생성하면 기본적으로 true(켜져있음)
			 * -> 매번 수동으로 끔
			 * 	 -> 개발자가 트랜잭션을 마음대로 제어하기 위해서
			 *   -> SQL성공 여부에따라 COMMIT or ROLLBACK 선택하기위해
			 */
			conn.setAutoCommit(false);
			
			/* PreparedStatement 객체 생성 */
			// -> 객체 생성과 동시에 SQL을 매개변수로 전달해서 담기
			// -> SQL 파악하기 위해서
			pstmt = conn.prepareStatement(sql);
			
			/* ?(placeholder)에 알맞는 값 세팅 */
			// pstmt.set자료형(?순서, 대입할값)
			pstmt.setString(1, id); // id 변수값이 ''추가하여 sql에 대입
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			
			/* SQL 수행 후 결과 반환 받기
			 * - SELECT : executeQuery() -> ResultSet 반환
			 * - DML : executeUpdate() -> 행의 개수(int) 반환
			 */
			pstmt.executeUpdate();
			
			// DML 수행으로 조작된 행의 개수 반환됨
			int result = pstmt.executeUpdate();
			
			/* result 값에 따라서 SQL 성공/실패 여부 출력
			 * + 트랜잭션 제어(commit/rollback)
			 */
			if(result > 0) {
				System.out.println(id + "님이 추가 되었습니다.");
				conn.commit(); // COMMIT 수행 -> INSERT 내역 DB 반영
			} else {
				System.out.println("추가 실패");
				conn.rollback(); // 추가 실패시 ROLLBACK수행
			}

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 사용한 JDBC 객체 자원 반환
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
