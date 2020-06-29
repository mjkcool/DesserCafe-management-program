import java.sql.*;

public class CreateTables_DB {
	public CreateTables_DB() {
		String url = "jdbc:sqlite:DessertCafe.db";
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs = null;
		
		try
		{
			//����̹� ����
			Class.forName("org.sqlite.JDBC");
			//mySQL����
			conn = DriverManager.getConnection(url);
			System.out.println("SQLite DB connected");
			//DB ����
			stmt = conn.createStatement();
			//������ �� DB�� �°� ����
			
			String SQL = "CREATE TABLE IF NOT EXISTS lastrecord(lr_id INTEGER(11) NOT NULL PRIMARY key, recordeddate varchar(100) NOT NULL, income int(11) NOT NULL, ordernum int(11) NOT NULL)";
			stmt.executeUpdate(SQL);
			SQL = "CREATE TABLE IF NOT EXISTS menu(menu_id INTEGER(11) NOT NULL PRIMARY key, menu_name varchar(100) NOT NULL, price int(10) NOT NULL)";
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
