import java.sql.*;

/*지난 기록 보기 부분의 데이터를 저장하는 클래스*/
public class addLastRecords_DB {	
		public addLastRecords_DB(String sid, String sdate, String sincome, String sordernum) {
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
				//데이터 형 DB에 맞게 변경
				int id=Integer.parseInt(sid);
				int income = Integer.parseInt(sincome);
				int ordernum = Integer.parseInt(sordernum);
				
				int i = 0;
				for(; i < LastRecords.recordsIndex; i++)
				{   //recordsIndex의 인덱스
					String SQL1 = "select * from lastrecord where lr_id="+i+";";
					rs/*포인터가 위치한 행(초기:0)*/ = stmt.executeQuery(SQL1);
					if(!rs.next())  //곧장 선택한 행이 비어있는경우(포인터를 다음행으로 이동하면 이전 행이 선택됨) 
						break; //선택과정을 끝낸당
				}//end of for			
				
				//새 기록을 포인터가 가리키고 있는 열에 추가
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
