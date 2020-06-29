import java.sql.*;

public class updateMenu_DB {
	public updateMenu_DB(String sid, String sname, String sprice) {
		String url = "jdbc:sqlite:DessertCafe.db";

		Connection conn=null;
		Statement stmt=null;
		ResultSet rs = null;
		Menu s;
		
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
			int price=Integer.parseInt(sprice);
			int id = Integer.parseInt(sid);
			String SQL = "update menu set menu_name='"+sname+"', price="+price+" where menu_id="+id+";";
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
			
		SortDB sort = new SortDB();
	}
}
