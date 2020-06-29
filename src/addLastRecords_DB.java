import java.sql.*;

/*���� ��� ���� �κ��� �����͸� �����ϴ� Ŭ����*/
public class addLastRecords_DB {	
		public addLastRecords_DB(String sid, String sdate, String sincome, String sordernum) {
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
				int id=Integer.parseInt(sid);
				int income = Integer.parseInt(sincome);
				int ordernum = Integer.parseInt(sordernum);
				
				int i = 0;
				for(; i < LastRecords.recordsIndex; i++)
				{   //recordsIndex�� �ε���
					String SQL1 = "select * from lastrecord where lr_id="+i+";";
					rs/*�����Ͱ� ��ġ�� ��(�ʱ�:0)*/ = stmt.executeQuery(SQL1);
					if(!rs.next())  //���� ������ ���� ����ִ°��(�����͸� ���������� �̵��ϸ� ���� ���� ���õ�) 
						break; //���ð����� ������
				}//end of for			
				
				//�� ����� �����Ͱ� ����Ű�� �ִ� ���� �߰�
				String SQL = "insert into lastrecord(lr_id, recordeddate, income, ordernum) values(" + id + ",'"+sdate +"',"+income+","+ ordernum + ");";
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
