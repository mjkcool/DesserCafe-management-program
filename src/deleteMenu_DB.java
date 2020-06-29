import java.sql.*;

public class deleteMenu_DB {
	public deleteMenu_DB(String si) {
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
			//메뉴 불러오기
			int i=Integer.parseInt(si);
			String SQL = "delete from menu where menu_id="+i+";";
			stmt.executeUpdate(SQL);
			
			
			for(int k=i+1; k<12; k++) {
				SQL = "update menu set menu_id="+(k-1)+" where menu_id="+k+";";
				stmt.executeUpdate(SQL);				
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
		//SortDB sort = new SortDB();
	}
}

