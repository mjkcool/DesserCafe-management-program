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
			//����̹� ����
			Class.forName("org.sqlite.JDBC");
			//mySQL����
			conn = DriverManager.getConnection(url);
			System.out.println("SQLite DB connected");
			//DB ����
			stmt = conn.createStatement();
			//�޴� �ҷ�����
			int i=Integer.parseInt(i1);
			String SQL = "select menu_name, price from menu where menu_id="+i+";";
			rs=stmt.executeQuery(SQL);
			
			//DB���� �ҷ��� �޴� �̸��� �� �߰�
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

