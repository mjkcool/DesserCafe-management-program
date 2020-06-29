import java.util.Arrays;

class Order{	
		/*�ֹ��� �� �ִ� �޴� ������ �ѵ�(�ӽð� ����)*/ 
		static final int ordermenuLength = 2;
		
		/*�� �ν��Ͻ��� ����ִ����� ���� ����*/ private boolean isFilled = false;
		/*[sameline��]�ֹ��޴� �̸� �迭*/ private String orderName[] = new String[ordermenuLength];
		/*[sameline��]�ֹ��޴� ���� �迭*/ private int orderQuantity[] = new int[ordermenuLength];
		/*[sameline��]�����ݾ� �迭*/ private int finalPrice;
		/*�ֹ�(������)��ȣ*/ private int ordernum;
		
		
		/*�ʱ� ������ method, �ֹ� ���� ���� �޼���*/
		public Order() {
			for(int i=0; i<ordermenuLength; i++) {
				orderName[i] = "";
				orderQuantity[i] = 0;
			}
			finalPrice = 0;
			ordernum = 0;
			isFilled = false;
		}
		
		/*�ܢ¢����ֹ� ���� �߰� �޼��墸���¡�*/
		public void reOrder(String name[], int Quant[], int price, int ordernum) {
			isFilled = true;
			this.orderName[0] = name[0]; this.orderName[1] = name[1];
			this.orderQuantity[0] = Quant[0]; this.orderQuantity[1] = Quant[1];
			this.finalPrice = price; 
			this.ordernum = ordernum; //������ ��ȣ
		}
		
		
		/*�ֹ����� ���� �޼���*/
		public void deleteOrder() {
			for(int i=0; i<ordermenuLength; i++) {
				orderName[i] = "";
				orderQuantity[i] = 0;
			}
			finalPrice = 0;
			ordernum = 0;
			isFilled = false;
		}

		
		/* ���� ���ִ� ���� ��ȯ */
		public boolean isFilled() {
			return isFilled;
		}

		/* �ֹ��޴� ��ȯ */
		public String getOrderName(int n) {
			return orderName[n];
		}

		/* �ֹ��� ��ȯ */
		public int getOrderQuantity(int n) {
			return orderQuantity[n];
		}

		/* �Ѱ����ݾ� ��ȯ */
		public int getFinalPrice() {
			return finalPrice;
		}
		
		/* �ֹ���ȣ ��ȯ */
		public int getOrdernum() {
			return ordernum;
		}
		
		/* �ֹ� ��ȣ ���� */
		public int setOrdernum(int num) {
			return this.ordernum = num;
		}


		@Override
		public String toString() {
			return "Order [isFilled=" + isFilled + ", orderName=" + Arrays.toString(orderName) + ", orderQuantity="
					+ Arrays.toString(orderQuantity) + ", finalPrice=" + finalPrice + ", ordernum=" + ordernum + "]";
		}
		
		
	
}

