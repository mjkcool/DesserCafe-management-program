public class LastRecords{
	
		/*����*/private String date;
		/*���� �Ǹż���*/private int earnings_record;
		/*���� �Ǹŷ�*/ private int sales_record;
		
		/*������ �� �ִ� ��� ������ �ѵ� - ���� ����*/static int recordsIndex = 14;

		/* �ʱ� ������ , �ʱ�ȭ */
		public LastRecords() {
			date = "";
			earnings_record = 0;
			sales_record = 0;
		}
		
		/*����ڿ� ���� ��ü setter*/
		public void setRecord(String date, int earnings, int sales) {
			this.date = date;
			this.earnings_record = earnings;
			this.sales_record = sales;
		}
		
		/*���� getter*/
		public String getDate() {
			return date;
		}

		/*���� �Ǹż��� getter*/
		public int getEarnings_record() {
			return earnings_record;
		}

		/*���� �Ǹŷ� getter*/
		public int getSales_record() {
			return sales_record;
		}

}
