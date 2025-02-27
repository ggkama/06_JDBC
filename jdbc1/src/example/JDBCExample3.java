package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample3 {
	public static void main(String[] args) {
		
		// EMPLOYEE 테이블에서
		// 급여를 300만 이상 500만 이하로 받는 사원의
		// 사번, 이름, 부서코드, 급여를
		// 급여 내림차순으로 조회
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// Oracle JDBC Driver를 메모리에 적재(Load) == 객체로 만듬
			Class.forName("oracle.jdbc.OracleDriver");
			
			// DB연결정보
			String type = "jdbc:oracle:thin:@";
			String host = "112.221.156.34";
			String port = ":12345";
			String dbName = ":XE";
			String userName = "KH08_KTY";
			String password = "KH1234";
			
			// Connection 객체를 생성해서 얻어오기
			conn = DriverManager.getConnection(
					type + host + port + dbName, // DB URL 
					userName, 
					password
			);
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY ");
			sb.append("FROM EMPLOYEE ");
			sb.append("WHERE SALARY >= 3000000 AND SALARY <5000000 ");
			sb.append("ORDER BY SALARY DESC");
			
			String sql = sb.toString();
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptCode= rs.getString("DEPT_CODE");
				int salary = rs.getInt("SALARY");
				
				System.out.printf(
						"%s / %s / %s / %d \n",
						empId,empName,deptCode,salary
					);
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			
		}try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		
	}
	
	
}
