import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class resetRecords_DB {
	public resetRecords_DB() {
		String url = "jdbc:sqlite:DessertCafe.db";

		Connection conn=null;
		Statement stmt=null;
		ResultSet rs = null;
		
		try
		{
			//드라이버 연결
			Class.forName("org.sqlite.JDBC");
			//mySQL접속
			conn = DriverManager.getConnection(url);
			System.out.println("SQLite DB connected");
			//DB 접속
			stmt = conn.createStatement();
			
			String SQL = "select * from lastrecord;";
			
			rs = stmt.executeQuery(SQL);

			SQL = "delete from lastrecord;";
			stmt.executeUpdate(SQL);
	
							
		}
		catch(ClassNotFoundException cnfe){
			cnfe.printStackTrace();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		finally{
			if(conn != null) {
				try {
					conn.close();
				}
				catch(SQLException se){
						
				}
			}
				
			if(stmt != null){
				try{
					stmt.close();
				}
				catch(SQLException se){
						
				}
			}
				
			if(rs != null){
				try
				{
					rs.close();
				}
				catch(SQLException se)
				{
						
				}
			}//end of if
			
		}//end of finally
			
	}
}
