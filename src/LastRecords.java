public class LastRecords{
	
		/*제목*/private String date;
		/*지난 판매수입*/private int earnings_record;
		/*지난 판매량*/ private int sales_record;
		
		/*저장할 수 있는 기록 개수의 한도 - 변경 가능*/static int recordsIndex = 14;

		/* 초기 생성자 , 초기화 */
		public LastRecords() {
			date = "";
			earnings_record = 0;
			sales_record = 0;
		}
		
		/*사용자에 의한 전체 setter*/
		public void setRecord(String date, int earnings, int sales) {
			this.date = date;
			this.earnings_record = earnings;
			this.sales_record = sales;
		}
		
		/*제목 getter*/
		public String getDate() {
			return date;
		}

		/*지난 판매수입 getter*/
		public int getEarnings_record() {
			return earnings_record;
		}

		/*지난 판매량 getter*/
		public int getSales_record() {
			return sales_record;
		}

}