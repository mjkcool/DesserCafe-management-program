import java.sql.*;
import javax.swing.JTextField;
//
public class SortDB {
		
		
	public SortDB() {
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
			
			for(int i = 0; i < 12; i++)
			{
				String SQL = "select * from menu where menu_id="+i+";";
				rs = stmt.executeQuery(SQL);
				
				if(!rs.next()) { //���� ĭ�� ���������
					int j = i+1;
					for(; j < 12; j++) {
						String SQL1 = "select * from menu where menu_id="+j+";";
						rs = stmt.executeQuery(SQL1);
						if(rs.next()){ //�޿��� �Ⱥ��������
							//��ü�� �ƴ� �޿��� ������ ����(����) ����.
							String SQL2 = "update menu set menu_id="+rs.getInt("menu_id")+", menu_name='"+rs.getString("menu_name")+"', price="+rs.getInt("price")+" where menu_id="+i+";";
							stmt.executeUpdate(SQL2);
							String SQL3 = "delete from menu where menu_id="+j+";";
							stmt.executeUpdate(SQL3);
							break;
						}
						
					}
					
				}
					
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

