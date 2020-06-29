import java.sql.*;

import javax.swing.JTextField;

public class selectMenu_DB {
		String name;
		String price;
	public selectMenu_DB(String i1) {
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
			int i=Integer.parseInt(i1);
			String SQL = "select menu_name, price from menu where menu_id="+i+";";
			rs=stmt.executeQuery(SQL);
			
			//DB에서 불러온 메뉴 이름과 값 추가
			while(rs.next()){
				CafeTest.menu[i].reMenu(rs.getString("menu_name"),rs.getInt("price"));

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

