import java.sql.*;

public class arrayLastRecords_DB {
	public arrayLastRecords_DB() {
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
			
			String SQL4 = "delete from lastrecord where lr_id = 0;";
			stmt.executeUpdate(SQL4);
			
			
			for(int k=1; k<LastRecords.recordsIndex; k++) {
				SQL4 = "update lastrecord set lr_id="+(k-1)+" where lr_id="+(k)+";";
				stmt.executeUpdate(SQL4);				
			}			
										
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
