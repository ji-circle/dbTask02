import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBtask002 {

	//MySQL 연결 정보
	static final String DB_URL = "jdbc:mysql://localhost/cautask002?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //데이터베이스 주소
	static final String USER = "root"; //MySQL 사용자 이름
	static final String PASS = "mysql"; //MySQL 비밀번호

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			//MySQL 데이터베이스 연결
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//finish 입력할 때 까지 반복
			while (true) {
				System.out.print("검색할 빌딩 이름을 입력하세요(끝내려면 finish 입력): ");
				String buildingName = scanner.nextLine();

				// 종료하려면
				if (buildingName.equalsIgnoreCase("finish")) {
					System.out.println("검색을 종료합니다");
					break;
				}

				String query = "SELECT floor, number, capacity FROM room WHERE building = ?";

				stmt = conn.prepareStatement(query);
				stmt.setString(1, buildingName);
				rs = stmt.executeQuery();

				boolean exist = false;

				System.out.println("검색 결과:");
				while (rs.next()) {
					exist = true;
					int floor = rs.getInt("floor");
					int number = rs.getInt("number");
					int capacity = rs.getInt(3);
					System.out.println("floor: " + floor + ", number: " + number + ", capacity: " + capacity);
				}

				// 만약 검색 결과가 없다면
				if (!exist) {
					System.out.println("검색 결과가 없습니다.");
				}

				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println("SQLException : " + e);
		} finally {
			try {
				//NullPointerException 방지 위해 null 체크(실제로 생성되어있는지 확인 후에 자원 해제)
				if (rs != null)	rs.close();
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		scanner.close();
	}

}
