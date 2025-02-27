package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample5 {
	public static void main(String[] args) {
		
		// 부서명을 입력 받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순으로 조회
		
		Scanner sc = new Scanner(System.in);
		
		Connection conn= null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
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
			
			// 부서명 입력 받기
			System.out.println("=== 조회할 부서명 ===");
			System.out.println("부서명 입력 : ");
			String str = sc.nextLine();
			
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT E.EMP_ID ,E.EMP_NAME,D.DEPT_TITLE , J.JOB_NAME  ");
			sb.append("FROM EMPLOYEE E ");
			sb.append("LEFT JOIN DEPARTMENT D ON (D.DEPT_ID = E.DEPT_CODE) ");
			sb.append("JOIN JOB J ON (J.JOB_CODE = E.JOB_CODE) ");
			sb.append("WHERE D.DEPT_TITLE = ");
			sb.append("'"+str+"'");
			sb.append(" ORDER BY E.DEPT_CODE ASC");
			
			String sql = sb.toString();
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptCode = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");
				
				System.out.printf(
						"%s / %s / %s / %s \n",
						empId,empName,deptCode,jobName
						);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
