import java.sql.*;


import javax.swing.JTextField;

public class addMenu_DB {
		
		
	public addMenu_DB(String j1, String j2, String j3) {
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
			//메뉴 넣기
			int id=Integer.parseInt(j1);
			int price = Integer.parseInt(j3); 
			int i = 0;
			for(; i < 12; i++)
			{
				String SQL1 = "select * from menu where menu_id="+i+";";
				rs = stmt.executeQuery(SQL1);
				if(!rs.next())
					break;
			}
			
			String SQL = "insert into menu(menu_id, menu_name, price) values(" + i + ",'"+j2 +"',"+ price + ");";
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
